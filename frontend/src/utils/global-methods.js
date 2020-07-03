const layouts = (path) => () => import(`@/views/layouts/${path}`);
const pages = (path) => () => import(`@/views/pages/${path}`);

export { layouts, pages };
