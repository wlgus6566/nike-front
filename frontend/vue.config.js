module.exports = {
    runtimeCompiler: true,
    devServer: {
        proxy: {
            '/api': {
                target: 'http://10.80.1.212',
                // target: 'http://localhost:8081',
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
