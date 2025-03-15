# kubectl-extreme - Kubernetes Productivity Toolkit

## Installation

```bash
# Add these lines to your shell config (zshrc/bashrc):
export KUBECOLOR_PRESET="protanopia-dark"  # Color-blind friendly theme

# Add these aliases to your shell config (zshrc/bashrc):
alias kcgc='kubectl config get-contexts'
alias kgp='kubex get pods'
alias kgd='kubex get deployments'
alias kgns='kubex get namespace'
alias kgsvc='kubex get service'
alias kgvs='kubex get virtualservice'
alias kggw='kubex get gateway'
alias kgcm='kubex get configmap'
alias kghpa='kubex get hpa'
alias kgl='kubex get last'
alias kgse='kubex get serviceentry'
alias kgds='kubex get daemonset'
alias kgrs='kubex get replicaset'
alias kgsec='kubex get secrets'
alias kgn='kubex get nodes'
alias kd='kubex describe'
alias kdel='kubex delete'
alias kl='kubex logs'
alias keti='kubex exec'
alias kev='kubex events'
alias ke='kubex edit'
alias krr='kubex rollout restart'
alias ks='kubex switch'
```

### Command Reference
### Core Commands
| Command | Description                             | Equivalent               |
|---------|-----------------------------------------|--------------------------|
| k       | kubectl base command                    | `kubectl`                |
| kcgc    | List contexts                           | `kubectl config get-contexts` |

### Get Resources
These commands simplify `kubectl get` operations. The `in` keyword filters by namespace.
```bash
<command> [flags]
```
| Command | Resource Type      | Example Usage              |
|-------|--------------------|----------------------------|
| kgp   | Pods               | `kgp in dev-ns -l app=api` |
| kgd   | Deployments        | `kgd -w` (watch mode)      |
| kgns  | Namespaces         | `kgns`                     |
| kgsvc | Services           | `kgsvc --sort-by=.metadata.creationTimestamp` |
| kgvs  | VirtualServices    | `kgvs in istio-system`     |
| kggw  | Gateways           | `kggw`                     |
| kgcm  | ConfigMaps         | `kgcm`                     |
| kghpa | HorizontalPodAutoscalers | `kghpa`               |
| kgse  | ServiceEntries     | `kgse in default`          |
| kgds  | DaemonSets         | `kgds`                     |
| kgrs  | ReplicaSets        | `kgrs --show-labels`       |
| kgsec | Secrets            | `kgsec`                    |
| kgn   | Nodes              | `kgn`                     |

### Common Operations
| Command | Description                             | Example                   |
|---------|-----------------------------------------|---------------------------|
| kd      | Describe resource                       | `kd 1`                    |
| kdel    | Delete resource                         | `kdel 1`                  |
| kl      | View pod logs                           | `kl 1`                    |
| keti    | Execute command in container            | `keti 1`                  |
| kev     | View resource events                    | `kev 1`                   |
| ke      | Edit resource                           | `ke 1`                    |
| krr     | Restart deployments                     | `krr 1`                   |
| ks      | Switch context                         | `ks my-context`           |

### Advanced Features
- **Color Customization**: Edit `KUBECOLOR_PRESET` for different themes
- **Watch Integration**: `watch kgp` for real-time pod monitoring
- **Namespace Switching**: Use `in` clause for context filtering

## Examples

1. Watch deployments with color:
```bash
watch kgd
```

2. Get Istio VirtualServices in a namespace:
```bash
kgvs in istio-system
```

3. Tail logs for a pod:
```bash
kl my-pod --tail=100 -f
```

[//]: # (Generated by kubectl-boost v1.2.0)
