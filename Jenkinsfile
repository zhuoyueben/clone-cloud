node {
    def harbor_url="192.168.153.141:85"

    def harbor_project="zhangs"
    stage('Preparation') { // for display purposes
       checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'bb4e9a07-9ee8-42ed-a739-2653b268037c', url: 'https://github.com/zhuoyueben/clone-cloud.git']]])
    }
    stage('源码审查') {
		  def scannerHome = tool 'sonar-scanner'
		   withSonarQubeEnv('sonarQube6.7.4') {
		       sh """
		       cd ${module_name}
		       ${scannerHome}/bin/sonar-scanner
		       """
		   }
    }
    stage('Build') {
        sh """
        echo '项目打包，制作docker镜像'
        mvn -f ${module_name}  clean package  dockerfile:build
        """

        def image_name="mall-${module_name}:latest"

        sh """
        echo 'docker镜像打标签'
        docker tag ${image_name} ${harbor_url}/${harbor_project}/${image_name}
        """

        withCredentials([usernamePassword(credentialsId: '787c7333-1852-4911-a2a6-aea8f5441167', passwordVariable: 'password', usernameVariable: 'username')]) {
    // some block
            sh """
            echo 'harbor登录'
            docker login -u ${username} -p ${password} ${harbor_url}

            echo '镜像上传'
            docker push ${harbor_url}/${harbor_project}/${image_name}
            """
        }
    }
}