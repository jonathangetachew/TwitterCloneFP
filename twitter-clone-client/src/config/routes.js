import HomePage from '../pages/Home';
import TweetsPage from '../pages/Tweets';

export default [
    {
        title:"Home page",
        exact: true,
        path:"/",
        component: HomePage
    },
    {
        title:"Tweets Page",
        exact: true,
        path:"/tweets",
        component: TweetsPage
    }
]