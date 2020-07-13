const ansiRegex = require('ansi-regex');
module.exports = {
    lintOnSave: false,
    assetsDir: 'pc',
    indexPath: 'pc.html',
    outputDir: '../dist',
    transpileDependencies: [ansiRegex],
    devServer: {
        proxy: {
            '^/api': {
                target: 'http://10.80.1.212',
                changeOrigin: true,
                ws: true,
                pathReWrite: {
                    '^/api': '',
                },
            },
        },
        overlay: false,
        // port: 8081
    },
};
