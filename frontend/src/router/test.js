import {pages} from '../util/global-methods';

const routes = [
	{path: 'about', component: pages('about'), props: true, redirect:'about/introduce',
		children: [
			{path: 'introduce', component: pages('about/introduce'), props: true},
			{path: 'sitemap', component: pages('about/sitemap'), props: true},
		]
	},
];

export default routes;
