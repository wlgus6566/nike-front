module.exports = {
    root: true,
    env: {
        node: true,
    },
    extends: [
        'plugin:prettier/recommended',
        'plugin:vue/essential',
        'eslint:recommended',
        '@vue/prettier',
    ],
    plugins: ['prettier'],
    parserOptions: {
        parser: 'babel-eslint',
    },
    rules: {},
};
