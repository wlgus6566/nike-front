module.exports = {
	// runtimeCompiler: true,
	devServer: {
		proxy: {
			'^/api':{
				target:'http://niketest.com:8080',
				changeOrigin: true,
				ws:true,
				pathReWrite:{
					'^/api':''
				}
			}
		}
		// overlay: false,
		// port: 8081
	}
}