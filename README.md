# KubeX - kubectl eXtreme

**KubeX** is a kubectl wrapper that greatly enhances your kubectl experience by providing simplified commands and helpful shortcuts. It uses kubectl to carry out the actual Kubernetes commands.

## Dependencies

- **kubectl**: The Kubernetes command-line tool.
- **kubecolor**: (Optional) Provides colored kubectl output. Highly recommended.

## Installation
1.  **Install KubeX using brew**
```bash
brew tap chienphamvu/tap
brew install kubex
```

2.  **Add aliases to your shell config (optional - highly recommended):**

    Add these aliases - or any other aliases you can think of - to your shell config (`~/.zshrc` or `~/.bashrc`):

    ```bash
    alias kg='kubex get'
    alias kd='kubex describe'
    alias kdel='kubex delete'
    alias ke='kubex edit'
    alias keti='kubex exec'
    alias kev='kubex events'
    alias kl='kubex logs'
    alias kt='kubex top'
    alias kgp='kubex get pods'
    alias kgd='kubex get deployments'
    alias kgns='kubex get namespace'
    alias kgsa='kubex get serviceaccounts'
    alias kgcm='kubex get configmap'
    alias kgcr='kubex get clusterroles'
    alias kgcrb='kubex get clusterrolebindings'
    alias kgdr='kubex get destinationrule'
    alias kgds='kubex get daemonset'
    alias kgss='kubex get statefulsets'
    alias kgbc='kubex get backendconfig'
    alias kgfc='kubex get frontendconfig'
    alias kggw='kubex get cronjob'
    alias kghpa='kubex get hpa'
    alias kgi='kubex get ingress'
    alias kgl='kubex get last'
    alias kgn='kubex get nodes'
    alias kgpdb='kubex get pdb'
    alias kgr='kubex get roles'
    alias kgrb='kubex get rolebindings'
    alias kgrs='kubex get replicaset'
    alias kgsec='kubex get secrets'
    alias kgsvc='kubex get service'
    alias kgmc='kubex get managedcertificates'
    alias kggw='kubex get gateway'
    alias kgse='kubex get serviceentry'
    alias kgvs='kubex get virtualservice'
    alias krr='kubex rollout restart'
    alias ks='kubex switch'
    ```

## Usage

**KubeX** provides simplified commands for common Kubernetes operations, leveraging kubectl for a more efficient and user-friendly experience. `kubecolor` is highly recommended for colored output.

### Get Resources

This command does one simple but most important thing: displays the normal kubectl output with numbered lines. This greatly simplifies the subsequent commands you're going to execute on these resources (`describe`, `edit`, `logs`, etc.) since you won't have to type resource names or types (pod, deployment, etc.) again.

It also supports `in <namespace>` and `on <context>` options for easy context and namespace switching. **KubeX** uses fuzzy matching, so you don't need to provide the full namespace or context name; **KubeX** will find the matches and prompt you to select from the available options.

```bash
kubex get <resource> [in <namespace>] [on <context>] [<string>] [flags]
```
- `<resource>` can be any valid kubectl resource (`pod`, `deployment`, `service`, `serviceaccount`, etc.) or `last`, which is a special resource for **KubeX** that quickly shows the last list from the cache.
- `<namespace>` and `<context>` do not need to be an exact match. All subsequent commands will use this namespace and context by default.
- `<string>` is the filter string to further refine the output (using `grep` essentially).
- `[flags]` can be any valid kubectl flags.

## Examples

### Get Pods

```bash
kubex get pods
```
or with alias
```bash
kgp
```
Output:
```

    | CLUSTER    dev
    | NAMESPACE  app
    | RESOURCE   pods
    | SCOPE      global

       NAME                                         READY   STATUS    RESTARTS   AGE
    1  api-gateway-5c6d8c6b97-m7q6z                 1/1     Running   0          2d
    2  auth-service-7b9b8f5b9c-k9z4x                1/1     Running   0          2d
    3  catalog-service-6b8b9c7c6f-r2d5h             1/1     Running   0          2d
    4  order-service-84d659c78b-p8wlx               1/1     Running   0          2d
```

Other than the numbered list of pods, it also displays the current context details. This can be disabled using `KUBEX_DISABLE_CONTEXT_DETAILS`, see **Configurations** section below for more details.

- `CLUSTER`: your current context
- `NAMESPACE`: your current namespace
- `RESOURCE`: your current type of resources for this request
- `SCOPE`: the scope of this context (see `KUBEX_ISOLATED` in **Configurations** section below for more details).
    - `global`: this context is global and hence it affects all terminals that do not have `KUBEX_ISOLATED="true"` set.
    - `terminal`: this context is only for this terminal. This indicates that `KUBEX_ISOLATED="true"` is set for this terminal.

After a "get" command is run (to get `pods`, `deployments` or any other resources), any actions (`describe`, `edit`, `logs`, etc.) to interact with a specific resource will use its reference number. And resource type (pod, deployment, etc.) doesn't need to be specified on these commands since it already knows from the last resource type from the last "get".

### Describe a Pod

Describes the pod labeled as #1 from the previous "get pods" result.

```bash
kubex describe 1
```
or with alias
```bash
kd 1
```

Output:
```
Name:         api-gateway-5c6d8c6b97-m7q6z
Namespace:    default
... (rest of the describe output)
```

### View Pod Logs

View logs of the pod labeled as #1 from the previous "get pods" result.

```bash
kubex logs 1 -f
```
or with alias
```bash
kl 1 -f
```
Output:
```
... (pod log output)
```

### Execute Command in Container

Executes a command within the pod labeled as #1 from the previous "get pods" result.

```bash
kubex exec 1
```
or with alias
```bash
keti 1
```

### View Resource Events

View events of the pod labeled as #1 from the previous "get pods" result.

```bash
kubex events 1
```
or with alias
```bash
kev 1
```

### Fuzzy Matching

`[in <namespace>]` and `[on <context>]` options in `get` command use fuzzy matching so you don't have to type the full namespace/context name:
```
kubex get pods in app on dev
```
Or with alias:
```
kgp in app on dev
```
Output:
```
Multiple matching cluster found:

   1  dev-a
   2  dev-b
   3  dev-c

Enter the line number to select: 1
Switched to context "dev-a".
Multiple matching namespace found:

   1  ns-app-x
   2  ns-app-y

Enter the line number to select: 2
Switched default namespace to ns-app-y

    | CLUSTER    dev-a
    | NAMESPACE  ns-app-y
    | RESOURCE   pods
    | SCOPE      global

       NAME                                         READY   STATUS    RESTARTS   AGE
    1  api-gateway-5c6d8c6b97-m7q6z                 1/1     Running   0          2d
    2  auth-service-7b9b8f5b9c-k9z4x                1/1     Running   0          2d
    3  catalog-service-6b8b9c7c6f-r2d5h             1/1     Running   0          2d
    4  order-service-84d659c78b-p8wlx               1/1     Running   0          2d
```
And of course, it will not prompt for selection if there is only one matching found.

### Filter Resources
Quickly filter resources from the list with `kubex get <resource> <string>` command:
```
kubex get pods catalog
```
Or with alias
```
kgp catalog
```

Output:
```
    | CLUSTER    dev-a
    | NAMESPACE  ns-app-y
    | RESOURCE   pods
    | SCOPE      global

       NAME                                         READY   STATUS    RESTARTS   AGE
    3  catalog-service-6b8b9c7c6f-r2d5h             1/1     Running   0          2d
```
### Quickly accessing the last "get" resources
The last resource list of "get" command can be quickly re-summoned without actually executing kubectl command to get the resource
```
kubex get last
```
Or with alias
```
kgl
```
This is especially useful to be used together with `<string>` to quicky filter resources from the last list: `kubex get last <string>`

### Special Features

-   **`--gcp` flag**: When used with `kubex top` or `kubex logs`, this flag opens the corresponding resource's metrics or logs in the Google Cloud Console, assuming you are running in a GCP environment and have the necessary permissions.

    ```bash
    kubex top 1 --gcp  # Opens the pod's metrics in GCP Console
    kubex logs 1 --gcp # Opens the pod's logs in GCP Console
    ```

## Configurations

### Environment Variables

You can set these environment variables in your shell configuration file (e.g., `~/.zshrc` or `~/.bashrc`) using the `export` command, or directly in your terminal. Setting them in your shell configuration file makes them persistent across sessions.

-   **`KUBEX_DISABLE_CONTEXT_DETAILS`**: If set to `"true"`, disables the display of context details (cluster, namespace, resource type, scope) before listing resources.  For example: `export KUBEX_DISABLE_CONTEXT_DETAILS="true"`.
-   **`KUBEX_ISOLATED`**: By default, if `in <namespace>` or `on <context>` are used in a command, the namespace/context is automatically switched for all subsequent commands for all terminals since it actually changes the actual kubectl configs. If `KUBEX_ISOLATED` is set to `"true"` in a terminal, namespace/context switching only affects the current terminal. This is especially helpful if you are working on multiple namespaces/contexts at the same time.  You might want to put this variable in your `~/.zshrc` or `~/.bashrc` to always isolate terminals: `export KUBEX_ISOLATED="true"`.
-   **`KUBEX_EXACT_MATCH_NS`**: If set to `"true"`, disable fuzzy matching for namespace and use exact match instead.
-   **`KUBEX_EXACT_MATCH_CONTEXT`**: If set to `"true"`, disable fuzzy matching for context and use exact match instead.

### Tips
1. kubectl has support for `KUBE_EDITOR` to point to your preferred editor for the `edit` command instead of relying on the standard `vi`.
If you're using VS Code on macOS, you can set it as your default editor by adding this line to your `~/.bashrc` or `~/.zshrc`
```bash
export KUBE_EDITOR="/Applications/'Visual Studio Code.app'/Contents/Resources/app/bin/code --wait"
```
2. Config kubecolor color theme to match with what you like by setting `KUBECOLOR_PRESET` in your `~/.zshrc` or `~/.bashrc`. Please see their documentation https://kubecolor.github.io/customizing/themes/
