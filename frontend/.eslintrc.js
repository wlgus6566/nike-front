module.exports = {
    root: true,
    env: {
        node: true,
    },
    extends: [
        'airbnb-base',
        'plugin:prettier/recommended',
        'plugin:vue/essential',
        'eslint:recommended',
        '@vue/prettier',
    ],
    plugins: ['prettier'],
    parserOptions: {
        parser: 'babel-eslint',
    },
    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    },
};
