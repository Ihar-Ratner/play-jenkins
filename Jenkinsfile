pipeline {
    agent any

    triggers {
        pullRequest()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Linter') {
            when {
                branch pattern: "feature/.*", comparator: "REGEXP"
                changeRequest()
            }
            steps {
                echo "Building branch: ${env.BRANCH_NAME}"
                echo "GET ENV"
                sh 'env'
                withCredentials([usernamePassword(credentialsId: 'jenkins_admin', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh 'curl --user $USERNAME:$PASSWORD -X POST -F "jenkinsfile=<Jenkinsfile" http://193.168.35.100:8080/pipeline-model-converter/validate'
                }
            }
        }

        stage('Build') {
            when {
                branch 'development'
            }
            steps {
                echo "Building branch: ${env.BRANCH_NAME}"
                sh 'pwd'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing..'
                withCredentials([string(credentialsId: 'secret', variable: 'secret')]) {
                    sh '''
                        echo "The secret is ${secret}" > secret.txt
                    '''
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'production'
            }
            steps {
                echo 'Deploying....'
            }
        }
    }

    post {
        always {
            echo 'Clean Workspace after build'
            cleanWs()
        }
    }
}
