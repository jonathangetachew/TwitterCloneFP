import axios from 'axios';

export class TweetService {
  constructor(){
    this.axios = axios;
  }
  async getItems() {
   
    return await axios.get('/tweets');
  }

  async search(criteria){
    return await axios.get(`/search`);
  }
}

export default new TweetService();