package com.mpp.twitterclone.controllers.v2;

import com.mpp.twitterclone.controllers.v2.functions.TweetFunctions;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.TweetResourceAssembler;
import com.mpp.twitterclone.enums.TweetSource;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.services.TweetService;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Jonathan on 9/8/2019.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = TweetController.BASE_URL, produces = MediaTypes.HAL_JSON_VALUE)
public class TweetController {

	public static final String BASE_URL = "/api/v2/tweets";

	private final TweetService tweetService;

	private final TweetResourceAssembler tweetResourceAssembler;

	public TweetController(TweetService tweetService, TweetResourceAssembler tweetResourceAssembler) {
		this.tweetService = tweetService;
		this.tweetResourceAssembler = tweetResourceAssembler;
	}

	///> Get Mappings
	@ApiOperation(value = "Get all Tweets",
					notes = "This operation can only be done by an ADMIN.")
	@GetMapping
	public Resources<Resource<Tweet>> getAllTweets() {
		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources
				.apply(tweetService.findAll(), tweetResourceAssembler);

		return new Resources<>(tweets,
				linkTo(methodOn(TweetController.class).getAllTweets()).withSelfRel());
	}

	@ApiOperation(value = "Get All Tweets by Username",
					notes = "It can be done by any user.")
	@GetMapping("/user/{username}")
	public Resources<Resource<Tweet>> getAllTweetsByUsername(@PathVariable @Valid String username) {
		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
				tweetService.findAllTweetsByUsername(username), tweetResourceAssembler
			);

		return new Resources<>(tweets,
				linkTo(methodOn(TweetController.class).getAllTweetsByUsername(username)).withSelfRel());
	}

	@ApiOperation(value = "Get Top K Tweets by Username",
					notes = "It can be done by any user.")
	@GetMapping("/user/{username}/top/{k}")
	public Resources<Resource<Tweet>> getTopKFavoritedTweetsByUsername(@PathVariable @Valid String username,
	                                                          @PathVariable @Valid Long k) {
		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
				TweetFunctions.findTopKFavoritedTweets.apply(tweetService.findAllTweetsByUsername(username), k),
				tweetResourceAssembler
			);

		return new Resources<>(tweets,
				linkTo(methodOn(TweetController.class).getTopKFavoritedTweetsByUsername(username, k)).withSelfRel());
	}

	@ApiOperation(value = "Find Tweets with Specified Source (MOBILE / WEB)",
					notes = "It can be done by any user.")
	@GetMapping("/source/{source}")
	public Resources<Resource<Tweet>> getAllTweetsBySource(@PathVariable @Valid String source) {

		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
				TweetFunctions.findAllTweetsBySource.apply(tweetService.findAll(),
						source.toUpperCase().equals(TweetSource.MOBILE.toString()) ? TweetSource.MOBILE : TweetSource.WEB),
				tweetResourceAssembler
			);

		return new Resources<>(tweets,
				linkTo(methodOn(TweetController.class).getAllTweetsBySource(source)).withSelfRel());
	}

	@ApiOperation(value = "Find K Oldest Tweets",
					notes = "It can be done by any user.")
	@GetMapping("/old/{k}")
	public Resources<Resource<Tweet>> getKOldestTweets(@PathVariable @Valid Long k) {

		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
				TweetFunctions.findKOldestTweets.apply(tweetService.findAll(), k),
				tweetResourceAssembler
			);

		return new Resources<>(tweets,
				linkTo(methodOn(TweetController.class).getKOldestTweets(k)).withSelfRel());
	}

	@ApiOperation(value = "Find K Latest Tweets",
					notes = "It can be done by any user.")
	@GetMapping("/new/{k}")
	public Resources<Resource<Tweet>> getKLatestTweets(@PathVariable @Valid Long k) {

		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
				TweetFunctions.findKLatestTweets.apply(tweetService.findAll(), k),
				tweetResourceAssembler
			);

		return new Resources<>(tweets,
				linkTo(methodOn(TweetController.class).getKLatestTweets(k)).withSelfRel());
	}

	@ApiOperation(value = "Find Most Replied Tweets by ParentId",
					notes = "It can be done by any user.")
	@GetMapping("/mostreplied")
	public Resources<Resource<Tweet>> getMostRepliedTweetsByParentId() {

		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
				TweetFunctions.findMostRepliedTweetsByParentID.apply(tweetService.findAll()),
				tweetResourceAssembler
			);

		return new Resources<>(tweets,
				linkTo(methodOn(TweetController.class).getMostRepliedTweetsByParentId()).withSelfRel());
	}

	@ApiOperation(value = "Find Today's Tweets",
					notes = "It can be done by any user.")
	@GetMapping("/today")
	public Resources<Resource<Tweet>> getTodayTweets() {

		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
				TweetFunctions.findGivenDateTweets.apply(tweetService.findAll(), LocalDate.now()),
				tweetResourceAssembler
			);

		return new Resources<>(tweets,
				linkTo(methodOn(TweetController.class).getTodayTweets()).withSelfRel());
	}

	@ApiOperation(value = "Get Tweet by ID")
	@GetMapping("/{id}")
	public Resource<Tweet> getTweetById(@PathVariable @Valid String id) {
		return TweetFunctions.convertTweetToResource.apply(tweetService.findById(id), tweetResourceAssembler);
	}

	@ApiOperation(value = "This will get a list of all replies for a given tweet.")
	@GetMapping("/{id}/replies")
	public Resources<Resource<Tweet>> getTweetReplies(@PathVariable @Valid String id) {
		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
				tweetService.findAllReplies(id), tweetResourceAssembler
			);

		return new Resources<>(tweets,
				linkTo(methodOn(TweetController.class).getTweetReplies(id)).withSelfRel());
	}

	//Davaa start
//	@ApiOperation(value = "Find Tweets by date",
//					notes = "It can be done by any user.")
//	@GetMapping("/bydate/{d}")
//	public Resources<Resource<Tweet>> getTweetsByDate(@PathVariable @Valid String d) {
//
//		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
//				TweetFunctions.findTweetsByDate.apply(tweetService.findAll(), LocalDate.parse(d)),
//				tweetResourceAssembler
//			);
//
//		return new Resources<>(tweets,
//				linkTo(methodOn(TweetController.class).getTweetsByDate(d)).withSelfRel());
//	}

//	@ApiOperation(value = "Search tweets by keyword",
//					notes = "It can be done by any user.")
//	@GetMapping("/search/{keyword}")
//	public Resources<Resource<Tweet>> searchTweetsByKeyword(@PathVariable @Valid String keyword) {
//
//		List<Resource<Tweet>> tweets = TweetFunctions.convertTweetsToResources.apply(
//				TweetFunctions.searchTweetsByKeyword.apply(tweetService.findAll(),keyword),
//				tweetResourceAssembler
//			);
//
//		return new Resources<>(tweets,
//				linkTo(methodOn(TweetController.class).searchTweetsByKeyword(keyword)).withSelfRel());
//	}
	//Davaa end
	///> Post Mappings
	@ApiOperation(value = "Create a Tweet",
					notes = "This operation can only be done by an authenticated user.")
	@PostMapping("/create")
	public ResponseEntity<Resource<Tweet>> createTweet(@RequestBody Tweet tweet) throws URISyntaxException {
		Resource<Tweet> tweetResource = TweetFunctions.convertTweetToResource
				.apply(tweetService.create(tweet),tweetResourceAssembler);

		return ResponseEntity
				.created(new URI(tweetResource.getId().expand().getHref()))
				.body(tweetResource);
	}

	@ApiOperation(value = "Reply/Comment to/on a Tweet",
					notes = "This operation can only be done by an authenticated user.")
	@PostMapping("/{originalTweetId}/reply")
	public ResponseEntity<Resource<Tweet>> reply(@RequestBody Tweet replyTweet,
	                                             @PathVariable @Valid String originalTweetId) throws URISyntaxException {
		Resource<Tweet> tweetResource = TweetFunctions.convertTweetToResource.apply(
				tweetService.replyToTweet(replyTweet, originalTweetId), tweetResourceAssembler
			);

		return ResponseEntity
				.created(new URI(tweetResource.getId().expand().getHref()))
				.body(tweetResource);
	}

	@ApiOperation(value = "Favorite a Tweet",
					notes = "This operation can only be done by an authenticated user.")
	@PostMapping("/{id}/favorite")
	public ResponseEntity<Resource<Tweet>> favorite(@PathVariable @Valid String id) throws URISyntaxException {
		Resource<Tweet> tweetResource = TweetFunctions.convertTweetToResource.apply(
				tweetService.favoriteTweet(id, "test"), tweetResourceAssembler
			);

		return ResponseEntity
				.created(new URI(tweetResource.getId().expand().getHref()))
				.body(tweetResource);
	}

	///> Put Mappings
	@ApiOperation(value = "Update a Tweet",
			notes = "This operation can only be done by an authenticated user.")
	@PutMapping("/{originalTweetId}/update")
	public ResponseEntity<Resource<Tweet>> updateTweet(@RequestBody Tweet newTweet,
	                                                   @PathVariable @Valid String originalTweetId) throws URISyntaxException {
		Resource<Tweet> tweetResource = TweetFunctions.convertTweetToResource.apply(
				tweetService.update(newTweet, originalTweetId), tweetResourceAssembler
			);

		return ResponseEntity
				.created(new URI(tweetResource.getId().expand().getHref()))
				.body(tweetResource);
	}

	///> Delete Mappings
	@ApiOperation(value = "Delete Tweet by ID",
			notes = "This operation can only be done by the owner of the tweet.")
	@DeleteMapping("/{tweetId}/remove")
	public ResponseEntity<?> deleteTweet(@PathVariable @Valid String tweetId) {
		tweetService.deleteById(tweetId);

		Map<String, String> responseMessage = new HashMap<>();
		responseMessage.put("message", "Tweet Removed Successfully");

		return ResponseEntity
				.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseMessage);
	}
}
