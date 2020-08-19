const path = require('path');
const ansiRegex = require('ansi-regex');
module.exports = {
    lintOnSave: false,
    assetsDir: 'pc',
    indexPath: 'pc.html',
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
                target: 'https://devapi.nikespace.co.kr',
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
