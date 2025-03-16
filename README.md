# KubeX - Kubectl Extreme

KubeX is a command-line tool that enhances your kubectl experience by providing simplified commands and helpful shortcuts.

## Dependencies

- **kubectl**: The Kubernetes command-line tool.
- **kubecolor**: (Optional) For colored kubectl output. Highly recommended.

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
    alias kg='kubex get'
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
    alias kl='kubex logs'
    alias keti='kubex exec'
    alias kev='kubex events'
    alias ke='kubex edit'
    alias kdel='kubex delete'
    alias krr='kubex rollout restart'
    alias ks='kubex switch'
    ```

## Usage

KubeX provides simplified commands for common Kubernetes operations, leveraging `kubectl` for a more efficient and user-friendly experience. `kubecolor` is highly recommended for colored output.

*   **Listing resources:** `kubex get <resource> [options]`
    * `<resource>` can be any valid kubectl resource (`pod`, `deployment`, `service`, `serviceaccount`, etc.)
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

This command displays the normal kubectl output but with numbered lines.
It also supports the `in <namespace>` and `on <context>` options for easy filtering and context switching. KubeX uses fuzzy matching, so you don't need to provide the full namespace or context name; KubeX will find the matches and prompt you to select from the available options.

```bash
kubex get <resource> [in <namespace>] [on <context>] [<string>] [flags]
```
- `<resource>` can be any valid kubectl resource (`pod`, `deployment`, `service`, `serviceaccount`, etc.) or `last`, which is a special resource for KubeX that quickly shows the last list from the cache.
- `<namespace>` and `<context>` do not need to be an exact match.
- `<string>` is the filter string to further refine the output.
- `[flags]` can be any valid kubectl flags.

## Examples

### Get Pods

```bash
kubex get pods
```

```
       NAME                                         READY   STATUS    RESTARTS   AGE
    1  api-gateway-5c6d8c6b97-m7q6z                 1/1     Running   0          2d
    2  auth-service-7b9b8f5b9c-k9z4x                1/1     Running   0          2d
    3  catalog-service-6b8b9c7c6f-r2d5h             1/1     Running   0          2d
    4  order-service-84d659c78b-p8wlx               1/1     Running   0          2d
```

### Describe a Pod

Describes the pod labeled as #1 from the previous "get pods" result.

```bash
kubex describe 1
```

```
Name:         api-gateway-5c6d8c6b97-m7q6z
Namespace:    default
... (rest of the describe output)
```

### View Pod Logs

View logs of the pod labeled as #1 from the previous "get pods" result.

```bash
kubex logs 1
```

```
... (pod log output)
```

### Execute Command in Container

Executes a command within the pod labeled as #1 from the previous "get pods" result.

```bash
kubex exec 1
```

### View Resource Events

```bash
kubex events 1
```

## Configuration

### Environment Variables

You can set these environment variables in your shell configuration file (e.g., `~/.zshrc` or `~/.bashrc`) using the `export` command, or directly in your terminal. Setting them in your shell configuration file makes them persistent across sessions.

-   **`KUBEX_DISABLE_CONTEXT_DETAILS`**: If set to `"true"`, disables the display of context details (cluster, namespace, resource type, scope) before listing resources.  For example: `export KUBEX_DISABLE_CONTEXT_DETAILS="true"`.

-   **`KUBEX_ISOLATED`**: If set to `"true"`, isolates the namespace and context settings to the current terminal session. This is useful if you want to use KubeX with different contexts or namespaces in different terminals without affecting each other.  You might want to set this variable directly in the terminal for specific sessions: `export KUBEX_ISOLATED="true"`.

### Special Features

-   **`--gcp` flag**: When used with `kubex top` or `kubex logs`, this flag opens the corresponding resource's metrics or logs in the Google Cloud Console, assuming you are running in a GCP environment and have the necessary permissions.

    ```bash
    kubex top 1 --gcp  # Opens the pod's metrics in GCP Console
    kubex logs 1 --gcp # Opens the pod's logs in GCP Console
    ```
