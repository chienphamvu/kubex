# KubeX - Kubernetes Productivity Toolkit

KubeX is a command-line tool designed to enhance your Kubernetes experience by providing simplified commands and helpful shortcuts.

## Dependencies

- **kubectl**: The Kubernetes command-line tool.
- **kubecolor**: For colored kubectl output.
- **bash**: KubeX is a bash script, so a bash environment is required.
- **coreutils**: KubeX relies on standard core utilities like `grep`, `sed`, `awk`, `tail`, `nl`, `wc`, `cut`, `less`, `tty`, `tr`, and `mkdir`.

## Installation

1.  **Clone the repository:**

    ```bash
    git clone <repository_url>
    cd <repository_directory>
    ```

2.  **Copy `kubex` to a directory in your `PATH` (e.g., `/usr/local/bin` or `~/bin`):**

    ```bash
    sudo cp kubex /usr/local/bin/
    sudo chmod +x /usr/local/bin/kubex
    ```

    Alternatively, copy to `$HOME/.local/bin` and ensure that directory is in your `$PATH`.

3.  **Set up your `PATH` (if necessary):**

    If you copied `kubex` to a directory that's not already in your `PATH`, you'll need to add it.  Edit your shell configuration file (`~/.bashrc`, `~/.zshrc`, etc.) and add the following line:

    ```bash
    export PATH="$PATH:/usr/local/bin"  # Or the directory where you put kubex
    ```

    Then, source your shell configuration file:

    ```bash
    source ~/.bashrc  # Or the appropriate file for your shell
    ```

4.  **Add aliases to your shell config (optional):**

    Add these lines to your shell config (zshrc/bashrc):

    ```bash
    export KUBECOLOR_PRESET="protanopia-dark"  # Color-blind friendly theme

    # Add these aliases to your shell config (zshrc/bashrc):
    alias kcgc='kubectl config get-contexts'
    alias kgp='kubex get pods'
    alias kgd='kubex get deployments'
    alias kgns='kubex get namespace'
    alias kgsvc='kubectl get service'
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

## Usage

KubeX provides simplified commands for common Kubernetes operations. It leverages `kubectl` and `kubecolor` to provide a more efficient and user-friendly experience.

KubeX simplifies common `kubectl` commands. Here are some examples:

*   **Listing resources:** `kubex get <resource_type> [options]`
    *   Example: `kubex get pods` (or `kgp`) - Lists all pods in the current namespace.
    *   Example: `kubex get deployments in kube-system` (or `kgd in kube-system`) - Lists all deployments in the `kube-system` namespace.
    *   Example: `kubex get services -o wide` (or `kgsvc -o wide`) - Lists all services with detailed output.

*   **Describing resources:** `kubex describe <number>`
    *   Example: `kubex describe 1` (or `kd 1`) - Describes the resource corresponding to line number 1 in the last list.

*   **Viewing logs:** `kubex logs <number>`
    *   Example: `kubex logs 2` (or `kl 2`) - Shows the logs for the resource corresponding to line number 2 in the last list.
    *   Example: `kubex logs 2 -f` (or `kl 2 -f`) - Streams the logs for the resource corresponding to line number 2 in the last list.

*   **Executing commands:** `kubex exec <number> [command]`
    *   Example: `kubex exec 3` (or `keti 3`) - Opens a shell in the container of the resource corresponding to line number 3 in the last list.

*   **Deleting resources:** `kubex delete <number>`
    *   Example: `kubex delete 4` (or `kdel 4`) - Deletes the resource corresponding to line number 4 in the last list.

### Get Resources

These commands simplify `kubectl get` operations. The `in` keyword filters by namespace, and `on` keyword switch context.

```bash
<command> [flags]
```

| Command | Resource Type      | Example Usage              |
|-------|--------------------|----------------------------|
| `kgp`   | Pods               | `kgp in dev-ns -l app=api` |
| `kgd`   | Deployments        | `kgd -w` (watch mode)      |
| `kgns`  | Namespaces         | `kgns`                     |
| `kgsvc` | Services           | `kgsvc --sort-by=.metadata.creationTimestamp` |
| `kgvs`  | VirtualServices    | `kgvs in istio-system`     |
| `kggw`  | Gateways           | `kggw`                     |
| `kgcm`  | ConfigMaps         | `kgcm`                     |
| `kghpa` | HorizontalPodAutoscalers | `kghpa`               |
| `kgse`  | ServiceEntries     | `kgse in default`          |
| `kgds`  | DaemonSets         | `kgds`                     |
| `kgrs`  | ReplicaSets        | `kgrs --show-labels`       |
| `kgsec` | Secrets            | `kgsec`                    |
| `kgn`   | Nodes              | `kgn`                     |
| `kgl`   | Last list           | `kgl`                     |

### Common Operations

| Command | Description                             | Example                   |
|---------|-----------------------------------------|---------------------------|
| `kd`      | Describe resource                       | `kd 1`                    |
| `kdel`    | Delete resource                         | `kdel 1`                  |
| `kl`      | View pod logs                           | `kl 1`                    |
| `keti`    | Execute command in container            | `keti 1`                  |
| `kev`     | View resource events                    | `kev 1`                   |
| `ke`      | Edit resource                           | `ke 1`                    |
| `krr`     | Restart deployments                     | `krr 1`                   |
| `ks`      | Switch context                         | `ks my-context`           |

## Examples

### Get Pods

```bash
kgp
```

```
    1  NAME                                         READY   STATUS    RESTARTS   AGE
    2  api-gateway-5c6d8c6b97-m7q6z                 1/1     Running   0          2d
    3  auth-service-7b9b8f5b9c-k9z4x                1/1     Running   0          2d
    4  catalog-service-6b8b9c7c6f-r2d5h             1/1     Running   0          2d
    5  order-service-84d659c78b-p8wlx               1/1     Running   0          2d
```

### Describe a Pod

```bash
kd 1
```

```
Name:         api-gateway-5c6d8c6b97-m7q6z
Namespace:    default
... (rest of the describe output)
```

### View Pod Logs

```bash
kl 1
```

```
... (pod log output)
```

### Execute Command in Container

```bash
keti 1
```

```
/ #
```

### View Resource Events

```bash
kev 1
```

```
... (events related to the resource)
```

## Configuration

### Environment Variables

-   **`KUBEX_DISABLE_CONTEXT_DETAILS`**: If set to `"true"`, disables the display of context details (cluster, namespace, resource type, scope) before listing resources.

-   **`KUBEX_ISOLATED`**: If set to `"true"`, isolates the namespace and context settings to the current terminal session.  This prevents changes from affecting other terminals.

### Special Features

-   **`--gcp` flag**: When used with `kubex top` or `kubex logs`, this flag opens the corresponding resource's metrics or logs in the Google Cloud Console, assuming you are running in a GCP environment and have the necessary permissions.

    ```bash
    kubex top 1 --gcp  # Opens the pod's metrics in GCP Console
    kubex logs 1 --gcp # Opens the pod's logs in GCP Console
    ```

## Suggestions

*   **Tab Completion**:  Consider adding tab completion for `kubex` commands and resource types.  This can greatly improve usability.  You could generate a completion script using `complete` command in bash and source it in your shell configuration.
*   **More Aliases**: Add more aliases for common resource types and operations.  For example, `kga='kubex get all'` to get all resources in a namespace.
*   **Configuration File**:  Instead of environment variables, consider using a configuration file (e.g., `~/.kubex.conf`) to store settings.  This allows for more complex configurations and easier management.
*   **Error Handling**: Improve error handling to provide more informative messages to the user.  For example, check if `kubectl` and `kubecolor` are installed and provide instructions if they are not.
*   **Interactive Mode**: Implement an interactive mode where users can select resources from a list using fuzzy finding or other techniques.
*   **Update Mechanism**: Add a mechanism to easily update `kubex` to the latest version.
*   **Documentation**: Expand the documentation to cover all commands, options, and configuration settings.
