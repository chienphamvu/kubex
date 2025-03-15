#!/bin/bash

context_name=$1
source "$HOME/tools/kkfunction.sh"

kubectl config use-context "$context_name"
kubectl get namespaces > "/tmp/namespaces_$(kubectl config current-context).txt"
