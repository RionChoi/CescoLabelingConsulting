def lastTagName = ""
def dockerImage
def regName = "cesdocker.cesco.biz:12000"
def imageName = regName + "/develop/cescodemo/cescodemo"
def regUri = "http://" + regName

pipeline {
    agent any
    stages{
        stage('Git Clone Repository') {
            steps {
                // git clone
                git (
                    branch: 'develop',
                    credentialsId: 'cesco-git',
                    url: 'https://github.com/cesco-is/cesco-cesnet-api.git'
                )
                
                // 개발은 tag 를 사용하지 않음
                // tag settings
                //script {
                //    lastTagName = sh(returnStdout:  true, script: "git tag --sort=-creatordate | head -n 1").trim()
                //}
                
                //echo "last tag name : ${lastTagName}"
            }
        }
        
        stage('Container Image Build & Push') {
            steps {
                withDockerRegistry([url: regUri, credentialsId: "cesdocker-hub"]) {
                    // docker build
                    //sh "docker build . -t ${imageName}:${lastTagName}"
                    sh "docker build . -t ${imageName}:latest"
                    
                    // docker latest Tag
                    //sh "docker tag ${imageName}:${lastTagName} ${imageName}:latest"
                    
                    // docker image push
                    //sh "docker push ${imageName}:${lastTagName}"
                    sh "docker push ${imageName}:latest"
                    
                    // docker image delete
                    //sh "docker rmi ${imageName}:${lastTagName}"
                    sh "docker rmi ${imageName}:latest"
                }
            }
        }
        
        stage('Remote Server Deploy') {
            steps([$class: 'BapSshPromotionPublisherPlugin']) {
                sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                        sshPublisherDesc(
                            configName: "cesnet3-server",
                            verbose: true,
                            transfers: [
                                sshTransfer(
                                        cleanRemote: true,
                                        excludes: '',
                                        execCommand: '',
                                        execTimeout: 120000,
                                        flatten: false,
                                        makeEmptyDirs: true,
                                        noDefaultExcludes: false,
                                        patternSeparator: '[, ]+',
                                        remoteDirectory: '/cesnet-cescodemo',
                                        remoteDirectorySDF: false,
                                        removePrefix: 'ci/',
                                        sourceFiles: 'ci/docker-compose.develop.yaml'),
                                        
                                        // Server Deploy
                                        sshTransfer(execCommand: """
                                            cd /docker-data/jenkins-data/cescodemo
                                            docker stack deploy -c ./docker-compose.develop.yaml cescodemo --with-registry-auth
                                        """)
                            ]
                        )
                    ]
                )
            }
        }
    }
}
