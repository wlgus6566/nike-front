module.exports = {
    runtimeCompiler: true,
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost',
                changeOrigin: true,
                pathReWrite: {
                    '^/api': '',
                },
            },
        },
        overlay: false,
        port: 80,
    },
}
