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
                        ),
                        text(
                            defaultValue: 0,
                            name: amount
                        ),
                        text(
                            defaultValue: '',
                            name: initialDate
                        ),
                        text(
                            defaultValue: '',
                            name: finalDate
                        ),
                        text(
                            defaultValue: '0',
                            name: exercise
                        ),
                        text(
                            defaultValue: '0',
                            name: food
                        ),
                        text(
                            defaultValue: '0',
                            name: bolus
                        ),
                        text(
                            defaultValue: '0',
                            name: reading
                        )
                    ])

                    sh "./gradlew bootRun --args='${language} ${amount} ${initialDate} ${finalDate} exercise=${exercise} food=${food} bolus=${bolus} reading=${reading}'"
                }
            }
        }
    }
}
