pipeline {
    agent any

    stages {
        stage('Gerador') {
            steps {
                script {
                    properties([
                        text(
                            defaultValue: '',
                            name: language
                        )
                    ])

                    sh "./gradlew bootRun --args='${language}'"
                }
            }
        }
    }
}
