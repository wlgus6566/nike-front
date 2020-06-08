module.exports = {
    root: true,
    env: {
        node: true,
    },
    extends: [
        'airbnb',
        'plugin:vue/essential',
        'eslint:recommended',
        '@vue/prettier',
    ],
    plugins: ['prettier'],
    parserOptions: {
        parser: 'babel-eslint',
    },
    rules: {
        'prettier/prettier': ['error'],
        /*"no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",*/
    },
}
