#!/bin/bash

last_list_file="/tmp/last_list.txt"

grep_and_switch_namespace() {
  local namespace_substring=$1
  MATCH=""

  namespaces_file="/tmp/namespaces_$(kubectl config current-context).txt"

  if [[ $namespace_substring =~ ^[0-9]+$ ]] ; then
    namespace_substring=$(get_item_name $namespace_substring)
  fi

  if ! ([ -f "$namespaces_file" ]) || ([ -f "$namespaces_file" ] && ! grep -q "$namespace_substring" "$namespaces_file"); then
    touch "$namespaces_file"
    echo "Getting namespaces..."
    kubectl get namespaces > "$namespaces_file"
  fi

  # if grep -q "^${namespace_substring}\s" "$namespaces_file"; then
  #     # prioritize exact match
  #     namespace=$(grep "^${namespace_substring}\s" "$namespaces_file" | head -n 1 | awk '{print $1}')
  # else
  #   # otherwise grep the first match namespace
  #   namespace=$(grep "$namespace_substring" "$namespaces_file" | head -n 1 | awk '{print $1}')
  # fi

  handle_matching "$namespace_substring" "$namespaces_file" "namespace"

  # switch default namespace
  if [ -n "$MATCH" ]; then
    kubectl config set-context --current=true --namespace="$MATCH" > /dev/null
    echo "Switched default namespace to $MATCH"
  else
    echo "No namespace matches $namespace_substring!"
    exit 1
  fi
}

make_list_numbered() {
  local line_number=-1
  local tmpfile=$(mktemp)

  while IFS= read -r line; do
    if [[ "$line_number" -eq -1 ]]; then
      echo "$line" >> "$tmpfile"
    elif [[ "$line_number" -eq 0 ]]; then
      printf "  %s\n" "$line" >> "$tmpfile"
    else
      printf "%d %s\n" "$line_number" "$line" >> "$tmpfile"
    fi
    ((line_number++))
  done < "$last_list_file"

  mv "$tmpfile" "$last_list_file"
}

get_item_name() {
  local line_number="$1"

  # Extract the pod name using awk
  item_name=$(awk -v line_num="$line_number" '$1 == line_num {print $2}' "$last_list_file" | sed 's/\x1B\[[0-9;]\{1,\}[A-Za-z]//g')

  if [[ -n "$item_name" ]]; then
    echo "$item_name"
  else
    echo "Error: '$line_number' does not exist!"
    exit 1
  fi
}

list_items() {
  local object=$1

  shift

  if [[ "$@" == *"-w"* ]] || [[ "$@" == "--watch" ]]; then
    kubecolor get $object $@
  else
    echo "$object" > $last_list_file
    # echo "kubecolor get $object $@"
    kubecolor get $object --force-colors $@ >> $last_list_file
    make_list_numbered $last_list_file
    tail -n +2 $last_list_file
  fi
}

get_last_object_type() {
  head -n 1 "$last_list_file"
}

grep_and_switch_cluster() {
  local cluster=$1
  MATCH=""

  handle_matching "$cluster" "$(kubectl config get-contexts --output=name)" "cluster"
  kubectl config use-context "$MATCH"
}

handle_matching() {
  local grep_string="$1"
  local file="$2"
  local resource_type="$3"

  if [[ -z "$grep_string" || -z "$file" ]]; then
    echo "Usage: $0 <grep_string> <file>"
    exit 1
  fi

  if [[ ! -f "$file" ]]; then
    matching_lines=$(echo "$file" | grep -n "$grep_string")
  else
    matching_lines=$(tail -n +2 "$file" | grep -n "$grep_string")
  fi

  if [[ -z "$matching_lines" ]]; then
    echo "No matching ${resource_type} found."
    exit 0
  fi

  line_count=$(echo "$matching_lines" | wc -l)

  if [[ "$line_count" -eq 1 ]]; then
    MATCH=$(echo "$matching_lines" | cut -d':' -f2- | awk '{print $1}')
  elif [[ "$line_count" -gt 1 ]]; then
    echo "Multiple matching ${resource_type} found:"
    echo
    echo "$matching_lines" | awk -F: '{print $1 ". " $2}' | awk '{print $1 " " $2}'
    echo

    read -p "Enter the line number to select: " selected_line_number

    MATCH=$(echo "$matching_lines" | awk -F: -v line_num="$selected_line_number" '$1 == line_num {print $2}' | awk '{print $1}')
  else
    echo "Error: Unexpected condition."
    exit 1
  fi
}
