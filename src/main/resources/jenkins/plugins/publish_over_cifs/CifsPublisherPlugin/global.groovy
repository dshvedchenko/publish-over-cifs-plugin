// Namespaces
xml = namespace("http://www.w3.org/XML/1998/namespace")
j = namespace("jelly:core")
f = namespace("/lib/form")
poc = namespace("/lib/publish_over_cifs")


def m = descriptor.hostConfigurationFieldNames
def helpUrl = "/plugin/publish-over-cifs/help/global/"

f.section(description: _("hostconfig.section.description"), title: _("hostconfig.section.title")) {
  f.entry(title: _("hostconfig.entry")) {
    f.repeatable(var: "instance", header: _("hostconfig.dragAndDrop"), items: descriptor.hostConfigurations) {
      poc.blockWrapper {
        f.entry(help: "${helpUrl}name.html", title: m.name(), field: "name") {
          f.textbox()
        }
        f.entry(help: "${helpUrl}hostname.html", title: m.hostname(), field: "hostname") {
          f.textbox()
        }
        f.entry(help: "${helpUrl}username.html", title: m.username(), field: "username") {
          f.textbox()
        }
        f.entry(help: "${helpUrl}password.html", title: m.password(), field: "password") {
          input(name: "_.password", type: "password", value: instance?.encryptedPassword, class: "setting-input")
        }
        f.entry(help: "${helpUrl}remoteRootDir.html", title: _("remotePath"), field: "remoteRootDir") {
          f.textbox()
        }
        f.advanced() {
          f.entry(help: "${helpUrl}port.html", title: m.port(), field: "port") {
            f.textbox()
          }
          f.entry(help: "${helpUrl}timeOut.html", title: m.timeout(), field: "timeout") {
            f.textbox()
          }
          f.entry(help: "${helpUrl}bufferSize.html", title: _("hostconfig.field.bufferSize"), field: "bufferSize") {
            f.textbox()
          }
          f.entry(help: "${helpUrl}smbVersion.html", title: _("hostconfig.field.smbVersion"), field: "smbVersion") {
            select(name: "_.smbVersion", class: "setting-input") {
              jenkins.plugins.publish_over_cifs.CifsHostConfiguration.SmbVersions.values().each { ver ->
                 if(ver == instance?.smbVersion) {
                   option(value: ver.name(), ver.description, selected: '')
                 } else {
                   option(value: ver.name(), ver.description)
                 }
              }
            }
          }
        }
        f.validateButton(with: "name,hostname,username,password,remoteRootDir,port,timeout,bufferSize,poc-np.winsServer,smbVersion", method: "testConnection", progress: m.test_progress(), title: m.test_title(), checkMethod: "post")
        f.entry(title: "") {
          div(align: "right") {
            f.repeatableDeleteButton()
          }
        }
      }
    }
  }

  if(descriptor.enableOverrideDefaults) {
    f.advanced() {
      f.entry() {
        f.dropdownDescriptorSelector(default: descriptor.pluginDefaultsDescriptor, field: "defaults", title: descriptor.commonManageMessages.defaults())
      }
    }
  }
}
