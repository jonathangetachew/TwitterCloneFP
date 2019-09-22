import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import { TweetService } from './tweet.service';
import items from '../common/data.json';

// This sets the mock adapter on the default instance
const delayResponse = 200;
const mock = new MockAdapter(axios, {delayResponse});

class TweetServiceMock extends TweetService {
  async getTweets() {
    mock.onGet('/tweets').reply(200, {
      tweets: items //
    });

    const data = await super.getItems();
   
    return data.data.tweets;
  }

  async search(criteria) {
    if (criteria && typeof (criteria) == 'string' && /^[a-z0-9]+$/i.test(criteria)) {
      
      console.log('mock',items,`/search?${criteria}`);
      mock.onGet(`/search`).reply(200, {        
        tweets: items.filter(item => item.name.includes(criteria) || item.body.includes(criteria))
      });
    } else {
      mock.onGet(`/search`).reply(504, { message:"Invalid character set. Must be alphanum." });
    }
   
    const data = await super.search(criteria);
    console.log('dt',data.data.tweets);
    return data.data.tweets;
  }
}

export default new TweetServiceMock();