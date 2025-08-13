@Library("one-platform-jenkins")_

properties([
        [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
        parameters([
                booleanParam(name: 'LW_RELEASE', defaultValue: false, description: 'Release the index-stage-sdk.'),
                choice(name: 'LW_SCOPE', choices: ['patch', 'minor', 'major'], description: 'Release scope')
        ])
])

buildFusionPublicJavaLibraryK8s("11", [lw_release: "${params.LW_RELEASE}",lw_scope: "${params.LW_SCOPE}"] )
