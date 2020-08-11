const path = require('path');
const ansiRegex = require('ansi-regex');
module.exports = {
    lintOnSave: false,
    assetsDir: 'mo',
    indexPath: 'mo.html',
    outputDir: '../dist',
    transpileDependencies: [ansiRegex],
    configureWebpack: {
        resolve: {
            alias: {
                '@': path.join(__dirname, 'src/'),
            },
        },
    },
    devServer: {
        proxy: {
            '/api': {
                target: 'http://10.80.1.212',
                changeOrigin: true,
                ws: true,
                pathReWrite: {
                    '^/api': '',
                },
            },
        },
        overlay: false,
    },
};
