import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.10"

project {

    buildType(FirstBuild)
    buildType(SecondBuild)

    template(DefaultTemplate)

    subProject(FirstProject)
}

object FirstBuild : BuildType({
    name = "FirstBuild"

    vcs {
        root(DslContext.settingsRoot)
    }
})

object SecondBuild : BuildType({
    templates(DefaultTemplate)
    name = "SecondBuild"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            name = "Run third script"
            scriptContent = "echo Third step"
        }
        script {
            name = "Run fourth script"
            scriptContent = "echo Fourth script"
        }
    }
})

object DefaultTemplate : Template({
    name = "DefaultTemplate"

    steps {
        script {
            name = "Run first script"
            scriptContent = "echo First step"
        }
        script {
            name = "Run second script"
            scriptContent = "echo Second step"
        }
    }
})


object FirstProject : Project({
    name = "FirstProject"
    defaultTemplate = RelativeId("DefaultTemplate")
})
