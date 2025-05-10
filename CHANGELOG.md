# Change Log

- Add support for port forwarding
## v0.2.1

- Cache namespace when users do "kubex get namespace"
- Fix "kubex get <number>" for isolated case and kubecolor does not exist

## v0.2.0

- Add support for "kubex get <resource-type> in <number>" when the previous command is "kubex get ns"
- Support both names and numbers
- Support grep string for "get" command before "in" or "on"
- Support for "--gcp" flags for "kubex events"
- Initialize session config as soon as possible to persist isolated configuration
- Use kubecolor for logs
- Add support for "kubex get <number>"
- Fix handling namespace for "get <resource-type> in all"
- Fix "kubex events" and "kubex rr" command
- Fix handling resource-type and namespace for "get all"

## v0.1.3

- Fix handling isolated context of "exec" command

## v0.1.2

- Fix namespace display detail on "get" command
- Fix "no matching found" error handling

## v0.1.1

- Fix color for "get" command with watch option "-w"
- Fix issue if user doesn't install kubecolor

## v0.1.0

- Initial release
