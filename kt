#!/bin/bash

item_number=$1
shift

source "$HOME/tools/kkfunction.sh"

if [ -z "$item_number" ]; then
    kubecolor top
    exit 1
fi

object_type=$(get_last_object_type)
item_name=$(get_item_name "$item_number")

if [[ "$@" == *"--gcp"* ]]; then
  CURRENT_CONTEXT=$(kubectl config get-contexts | grep "*")
  NAMESPACE=$(echo "$CURRENT_CONTEXT" | awk '{print $5}')
  CLUSTER_PROJECT=$(echo "$CURRENT_CONTEXT" | awk '{print $3}' | awk -F'_' '{print $2}')

  open "https://console.cloud.google.com/monitoring/metrics-explorer;duration=PT1H?referrer=search&project=${CLUSTER_PROJECT}&pageState=%7B%22xyChart%22:%7B%22constantLines%22:%5B%5D,%22dataSets%22:%5B%7B%22plotType%22:%22LINE%22,%22targetAxis%22:%22Y1%22,%22timeSeriesFilter%22:%7B%22aggregations%22:%5B%7B%22crossSeriesReducer%22:%22REDUCE_MEAN%22,%22groupByFields%22:%5B%22resource.label.%5C%22container_name%5C%22%22%5D,%22perSeriesAligner%22:%22ALIGN_MEAN%22%7D%5D,%22apiSource%22:%22DEFAULT_CLOUD%22,%22crossSeriesReducer%22:%22REDUCE_MEAN%22,%22filter%22:%22metric.type%3D%5C%22kubernetes.io%2Fcontainer%2Fmemory%2Fused_bytes%5C%22%20resource.type%3D%5C%22k8s_container%5C%22%20metric.label.%5C%22memory_type%5C%22%3D%5C%22non-evictable%5C%22%20resource.label.%5C%22namespace_name%5C%22%3D%5C%22${NAMESPACE}%5C%22%20resource.label.%5C%22container_name%5C%22!%3D%5C%22istio-proxy%5C%22%20resource.label.%5C%22pod_name%5C%22%3D%5C%22${item_name}%5C%22%22,%22groupByFields%22:%5B%22resource.label.%5C%22container_name%5C%22%22%5D,%22minAlignmentPeriod%22:%2260s%22,%22perSeriesAligner%22:%22ALIGN_MEAN%22%7D%7D,%7B%22plotType%22:%22LINE%22,%22targetAxis%22:%22Y1%22,%22timeSeriesFilter%22:%7B%22aggregations%22:%5B%7B%22crossSeriesReducer%22:%22REDUCE_MEAN%22,%22groupByFields%22:%5B%22resource.label.%5C%22container_name%5C%22%22%5D,%22perSeriesAligner%22:%22ALIGN_MEAN%22%7D%5D,%22apiSource%22:%22DEFAULT_CLOUD%22,%22crossSeriesReducer%22:%22REDUCE_MEAN%22,%22filter%22:%22metric.type%3D%5C%22kubernetes.io%2Fcontainer%2Fmemory%2Flimit_bytes%5C%22%20resource.type%3D%5C%22k8s_container%5C%22%20resource.label.%5C%22namespace_name%5C%22%3D%5C%22${NAMESPACE}%5C%22%20resource.label.%5C%22container_name%5C%22!%3D%5C%22istio-proxy%5C%22%20resource.label.%5C%22pod_name%5C%22%3D%5C%22${item_name}%5C%22%22,%22groupByFields%22:%5B%22resource.label.%5C%22container_name%5C%22%22%5D,%22minAlignmentPeriod%22:%2260s%22,%22perSeriesAligner%22:%22ALIGN_MEAN%22%7D%7D,%7B%22plotType%22:%22LINE%22,%22targetAxis%22:%22Y1%22,%22timeSeriesFilter%22:%7B%22aggregations%22:%5B%7B%22crossSeriesReducer%22:%22REDUCE_MEAN%22,%22groupByFields%22:%5B%22resource.label.%5C%22container_name%5C%22%22%5D,%22perSeriesAligner%22:%22ALIGN_MEAN%22%7D%5D,%22apiSource%22:%22DEFAULT_CLOUD%22,%22crossSeriesReducer%22:%22REDUCE_MEAN%22,%22filter%22:%22metric.type%3D%5C%22kubernetes.io%2Fcontainer%2Fmemory%2Frequest_bytes%5C%22%20resource.type%3D%5C%22k8s_container%5C%22%20resource.label.%5C%22namespace_name%5C%22%3D%5C%22${NAMESPACE}%5C%22%20resource.label.%5C%22container_name%5C%22!%3D%5C%22istio-proxy%5C%22%20resource.label.%5C%22pod_name%5C%22%3D%5C%22${item_name}%5C%22%22,%22groupByFields%22:%5B%22resource.label.%5C%22container_name%5C%22%22%5D,%22minAlignmentPeriod%22:%2260s%22,%22perSeriesAligner%22:%22ALIGN_MEAN%22%7D%7D%5D,%22options%22:%7B%22mode%22:%22COLOR%22%7D,%22y1Axis%22:%7B%22label%22:%22%22,%22scale%22:%22LINEAR%22%7D%7D%7D&inv=1&invt=AbqsyA"

  exit 0
fi

set -x
kubecolor top "$object_type" "$item_name" $@
