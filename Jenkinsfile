pipeline {
    agent any
    parameters{
        text(name:'LANGUAGE', defaultValue: '', description:'Language')
    }
    stages {
        stage('Gerador') {
            steps {
                script {
                    sh "./gradlew bootRun --args='${env.LANGUAGE}'"
                }
            }
        }
    }
}
