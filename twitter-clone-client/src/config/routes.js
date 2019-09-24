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
        title:"Get all tweets",
        exact: true,
        path:"/tweets",
        component: TweetsPage
    },
    {
        title:"Get today tweets",
        exact: true,
        path:"/tweets/today",
        component: TweetsPage
    },
    {
        title:"Get tweets from WEB",
        exact: true,
        path:"/tweets/source/WEB",
        component: TweetsPage
    },
    {
        title:"Get tweets from MOBILE",
        exact: true,
        path:"/tweets/source/MOBILE",
        component: TweetsPage
    },
    {
        title:"Get tweets from JOHN",
        exact: true,
        path:"/tweets/user/john",
        component: TweetsPage
    }
]