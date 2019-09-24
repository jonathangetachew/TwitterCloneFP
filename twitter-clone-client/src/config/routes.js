import HomePage from "../pages/Home";
import ContentPage from "../pages/Content";

export default [
  {
    title: "Home page",
    exact: true,
    path: "/",
    component: HomePage
  },
  {
    title: "Get all tweets",
    exact: true,
    path: "/tweets",
    key: "tweets",
    component: ContentPage
  },
  {
    title: "Get Tweet (test2) replies",
    exact: true,
    path: "/tweets/test2/replies",
    key: "tweets",
    component: ContentPage
  },
  {
    title: "Get today tweets",
    exact: true,
    path: "/tweets/today",
    key: "tweets",
    component: ContentPage
  },
  {
    title: "Get tweets from WEB",
    exact: true,
    path: "/tweets/source/WEB",
    key: "tweets",
    component: ContentPage
  },
  {
    title: "Get tweets from MOBILE",
    exact: true,
    path: "/tweets/source/MOBILE",
    key: "tweets",
    component: ContentPage
  },
  {
    title: "Get John's tweets",
    exact: true,
    path: "/tweets/user/john",
    key: "tweets",
    component: ContentPage
  },
  {
    title: "Find K(5) Latest Tweets",
    exact: true,
    path: "/tweets/new/5",
    key: "tweets",
    component: ContentPage
  },
  {
    title: "Find Most Replied Tweets",
    exact: true,
    path: "/tweets/mostreplied",
    key: "tweets",
    component: ContentPage
  },
  {
    title: "Get all users",
    exact: true,
    path: "/users",
    key: "users",
    component: ContentPage
  },
  {
    title: "Get Follower List by User ID",
    exact: true,
    path: "/users/edgar123/followers",
    key: "users",
    component: ContentPage
  },
  {
    title: "Get Following List by User ID",
    exact: true,
    path: "/users/davaa321/following",
    key: "users",
    component: ContentPage
  }
];
