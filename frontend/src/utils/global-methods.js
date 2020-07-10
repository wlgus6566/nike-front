const layouts = (path) => () => import(`@/views/layouts/${path}`);
const pages = (path) => () => import(`@/views/pages/${path}`);
const images = (path) => () => require(`@/assets/images/${path}`);

export { layouts, pages, images };
