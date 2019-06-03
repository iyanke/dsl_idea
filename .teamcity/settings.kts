import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.ideaDuplicates
import jetbrains.buildServer.configs.kotlin.v2018_2.ideaInspections
import jetbrains.buildServer.configs.kotlin.v2018_2.ideaRunner
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.SvnVcsRoot

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

version = "2019.1"

project {

    vcsRoot(svn)

    buildType(IdeaTest)

    features {
        feature {
            id = "PROJECT_EXT_5"
            type = "CloudImage"
            param("key-pair-name", "iy1")
            param("use-spot-instances", "false")
            param("security-group-ids", "sg-22baaa56,")
            param("profileId", "vmw-2")
            param("agent_pool_id", "-2")
            param("ebs-optimized", "false")
            param("instance-type", "t2.micro")
            param("amazon-id", "i-02ef2c854675c4fa1")
            param("source-id", "i-02ef2c854675c4fa1")
        }
        feature {
            id = "vmw-2"
            type = "CloudProfile"
            param("profileServerUrl", "")
            param("secure:access-id", "credentialsJSON:f9859dd6-4a52-4834-b64b-db5a1414c01b")
            param("system.cloud.profile_id", "vmw-2")
            param("total-work-time", "")
            param("description", "")
            param("cloud-code", "amazon")
            param("endpoint-url", "ec2.eu-west-1.amazonaws.com")
            param("enabled", "false")
            param("max-running-instances", "3")
            param("agentPushPreset", "")
            param("profileId", "vmw-2")
            param("name", "dddd")
            param("next-hour", "")
            param("secure:secret-key", "credentialsJSON:deb973b9-d3d7-4dac-a20a-f5a938124b38")
            param("terminate-idle-time", "30")
            param("not-checked", "")
        }
    }
}

object IdeaTest : BuildType({
    name = "IDEA_test"

    params {
        param("system.path.macro.MAVEN.REPOSITORY", ".m2")
        param("env.JDK_16", """C:\Java_16""")
    }

    vcs {
        root(svn)
    }

    steps {
        ideaRunner {
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar", "jre/lib/ext/jfxrt.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            jdk {
                name = "1.8"
                path = "%env.JDK_18%"
                patterns("jre/lib/*.jar", "jre/lib/ext/jfxrt.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("MAVEN_REPOSITORY", "%system.path.macro.MAVEN.REPOSITORY%")
            }
            jvmArgs = "-Xmx256m"
        }
        ideaDuplicates {
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar", "jre/lib/ext/jfxrt.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            jdk {
                name = "1.8"
                path = "%env.JDK_18%"
                patterns("jre/lib/*.jar", "jre/lib/ext/jfxrt.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("MAVEN_REPOSITORY", "%system.path.macro.MAVEN.REPOSITORY%")
            }
            jvmArgs = "-Xmx1G -XX:ReservedCodeCacheSize=240m"
            targetJdkHome = "%env.JDK_18%"
            lowerBound = 10
            discardCost = 0
            distinguishMethods = true
            distinguishTypes = true
            distinguishLiterals = true
            extractSubexpressions = true
        }
        ideaInspections {
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar", "jre/lib/ext/jfxrt.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            jdk {
                name = "1.8"
                path = "%env.JDK_18%"
                patterns("jre/lib/*.jar", "jre/lib/ext/jfxrt.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("MAVEN_REPOSITORY", "%system.path.macro.MAVEN.REPOSITORY%")
            }
            jvmArgs = "-Xmx512m -XX:ReservedCodeCacheSize=240m"
            targetJdkHome = "%env.JDK_18%"
        }
    }
})

object svn : SvnVcsRoot({
    name = "http://UNIT-1413.Labs.IntelliJ.Net/svn/idea/"
    url = "http://UNIT-1413.Labs.IntelliJ.Net/svn/idea/"
    userName = "admin1"
    password = "zxxb7b6b3cd34f24f95"
})
