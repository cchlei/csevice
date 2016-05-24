/*!
 * Feature Carousel, Version 1.3
 * http://www.bkosolutions.com
 *
 * Copyright 2011 Brian Osborne
 * Licensed under GPL version 3
 * brian@bkosborne.com
 *
 * http://www.gnu.org/licenses/gpl.txt
 */

(function($) {

  $.fn.featureCarousel = function (options) {

    // Adds support for multiple carousels on one page
    if (this.length > 1) {
      this.each(function() {
        $(this).featureCarousel(options);
      });
      return this;
    }
    
    // override the default options with user defined options
    options = $.extend({}, $.fn.featureCarousel.defaults, options || {});


    var pluginData = {
      currentCenterNum:     options.startingFeature,
      containerWidth:       0,
      containerHeight:      0,
      largeFeatureWidth:    0,
      largeFeatureHeight:   0,
      smallFeatureWidth:    0,
      smallFeatureHeight:   0,
      totalFeatureCount:    $(this).children("div").length,
      currentlyMoving:      false,
      featuresContainer:    $(this),
      featuresArray:        [],
      containerIDTag:       "#"+$(this).attr("id"),
      timeoutVar:           null,
      rotationsRemaining:   0,
      itemsToAnimate:       0,
      borderWidth:			    0
    };

    var preload = function(callback) {
      // user may not want to preload images
      if (options.preload == true) {
        var $imageElements = pluginData.featuresContainer.find("img");
        var loadedImages = 0;
        var totalImages = $imageElements.length;

        $imageElements.each(function (index, element) {
          // Attempt to load the images					
					var img = new Image();
          $(img).bind('load error', function () {
            // Add to number of images loaded and see if they are all done yet
            loadedImages++;
            if (loadedImages == totalImages) {
              // All done, perform callback
              callback();
            }
          });
					
					img.src = element.src;
        });
      } else {
        // if user doesn't want preloader, then just go right to callback
        callback();
      }
    }

    // Gets the feature container based on the number
    var getContainer = function(featureNum) {
      return pluginData.featuresArray[featureNum-1];
    }

    // get a feature given it's set position (the position that doesn't change)
    var getBySetPos = function(position) {
      $.each(pluginData.featuresArray, function () {
        if ($(this).data().setPosition == position)
          return $(this);
      });
    }

    // get previous feature number
    var getPreviousNum = function(num) {
      if ((num - 1) == 0) {
        return pluginData.totalFeatureCount;
      } else {
        return num - 1;
      }
    }

    // get next feature number
    var getNextNum = function(num) {
      if ((num + 1) > pluginData.totalFeatureCount) {
        return 1;
      } else {
        return num + 1;
      }
    }


    var setupFeatureDimensions = function() {

      pluginData.containerWidth = pluginData.featuresContainer.width();
      pluginData.containerHeight = pluginData.featuresContainer.height();

      // Grab the first image for reference
      var $firstFeatureImage = $(pluginData.containerIDTag).find(".carousel-image:first");

      // Large Feature Width
      if (options.largeFeatureWidth > 1)
        pluginData.largeFeatureWidth = options.largeFeatureWidth;
      else if (options.largeFeatureWidth > 0 && options.largeFeatureWidth < 1)
        pluginData.largeFeatureWidth = $firstFeatureImage.width() * options.largeFeatureWidth;
      else
        pluginData.largeFeatureWidth = $firstFeatureImage.outerWidth();
      // Large Feature Height
      if (options.largeFeatureHeight > 1)
        pluginData.largeFeatureHeight = options.largeFeatureHeight;
      else if (options.largeFeatureHeight > 0 && options.largeFeatureHeight < 1)
        pluginData.largeFeatureHeight = $firstFeatureImage.height() * options.largeFeatureHeight;
      else
        pluginData.largeFeatureHeight = $firstFeatureImage.outerHeight();
      // Small Feature Width
      if (options.smallFeatureWidth > 1)
        pluginData.smallFeatureWidth = options.smallFeatureWidth;
      else if (options.smallFeatureWidth > 0 && options.smallFeatureWidth < 1)
        pluginData.smallFeatureWidth = $firstFeatureImage.width() * options.smallFeatureWidth;
      else
        pluginData.smallFeatureWidth = $firstFeatureImage.outerWidth() / 2;
      // Small Feature Height
      if (options.smallFeatureHeight > 1)
        pluginData.smallFeatureHeight = options.smallFeatureHeight;
      else if (options.smallFeatureHeight > 0 && options.smallFeatureHeight < 1)
        pluginData.smallFeatureHeight = $firstFeatureImage.height() * options.smallFeatureHeight;
      else
        pluginData.smallFeatureHeight = $firstFeatureImage.outerHeight() / 2;
    }

    var setupCarousel = function() {
      // Set the total feature count to the amount the user wanted to cutoff
      if (options.displayCutoff > 0 && options.displayCutoff < pluginData.totalFeatureCount) {
        pluginData.totalFeatureCount = options.displayCutoff;
      }

      // fill in the features array
      pluginData.featuresContainer.find(".carousel-feature").each(function (index) {
        if (index < pluginData.totalFeatureCount) {
          pluginData.featuresArray[index] = $(this);
        }
      });


      if (pluginData.featuresContainer.find(".carousel-feature").first().css("borderLeftWidth") != "medium") {
        pluginData.borderWidth = parseInt(pluginData.featuresContainer.find(".carousel-feature").first().css("borderLeftWidth"))*2;
      }

      // Place all the features in a center hidden position to start off
      pluginData.featuresContainer
        // Have to make the container relative positioning
        .find(".carousel-feature").each(function () {
          // Center all the features in the middle and hide them
          $(this).css({
            'left': (pluginData.containerWidth / 2) - (pluginData.smallFeatureWidth / 2) - (pluginData.borderWidth / 2),
            'width': pluginData.smallFeatureHeight,
            'height': pluginData.smallFeatureHeight,
            'bottom': options.smallFeatureOffset + options.bottomPadding,
            'opacity': 1
          });
        })
        // Set all the images to small feature size
        .find(".carousel-image").css({
          'width': pluginData.smallFeatureWidth
        });
        
      // set position to relative of captions if displaying below image
      if (options.captionBelow) {
        pluginData.featuresContainer.find('.carousel-caption').css('position','relative');
      }

      // figure out number of items that will rotate each time
      if (pluginData.totalFeatureCount < 4) {
        pluginData.itemsToAnimate = pluginData.totalFeatureCount;
      } else {
        pluginData.itemsToAnimate = 4;
      }

      pluginData.featuresContainer.find(".carousel-caption")
        .hide();
    }

    var setupFeaturePositions = function() {
      // give all features a set number that won't change so they remember their
      // original order
      $.each(pluginData.featuresArray, function (i) {
        $(this).data('setPosition',i+1);
      });

      var oneBeforeStarting = getPreviousNum(options.startingFeature);
      pluginData.currentCenterNum = oneBeforeStarting;

      // Center feature will be position 1
      var $centerFeature = getContainer(oneBeforeStarting);
      $centerFeature.data('position',1);

      // Everything before that center feature...
      var $prevFeatures = $centerFeature.prevAll();
      $prevFeatures.each(function (i) {
        $(this).data('position',(pluginData.totalFeatureCount - i));
      });

      // And everything after that center feature...
      var $nextFeatures = $centerFeature.nextAll();
      $nextFeatures.each(function (i) {
        if ($(this).data('setPosition') != undefined) {
          $(this).data('position',(i + 2));
        }
      });

      // if the counter style is for including number tags in description...
      if (options.counterStyle == 'caption') {
        $.each(pluginData.featuresArray, function () {
          var pos = getPreviousNum($(this).data('position'));
          var $numberTag = $("<span></span>");
          $numberTag.addClass("numberTag");
          $numberTag.html("("+ pos + " of " + pluginData.totalFeatureCount + ") ");
          $(this).find('.carousel-caption p').prepend($numberTag);
        });
      }
    }

    var setupTrackers = function()
    {
      if (options.trackerIndividual) {
        // construct the tracker list
        var $list = $("<ul></ul>");
        $list.addClass("tracker-individual-container");
        for (var i = 0; i < pluginData.totalFeatureCount; i++) {
          // item position one plus the index
          var counter = i+1;

          // Build the DOM for the tracker list
          var $trackerBlip = $("<div>"+counter+"</div>");
          $trackerBlip.addClass("tracker-individual-blip");
          $trackerBlip.css("cursor","pointer");
          $trackerBlip.attr("id","tracker-"+(i+1));
          var $listEntry = $("<li></li>");
          $listEntry.append($trackerBlip);
          $listEntry.css("float","left");
          $listEntry.css("list-style-type","none");
          $list.append($listEntry);
        }
        // add the blip list and then make sure it's visible
        $(pluginData.containerIDTag).append($list);
        $list.hide().show();
      }
      
      if (options.trackerSummation) {
        // Build the tracker div that will hold the tracking data
        var $tracker = $('<div></div>');
        $tracker.addClass('tracker-summation-container');
        // Collect info in spans
        var $current = $('<span></span>').addClass('tracker-summation-current').text(options.startingFeature);
        var $total = $('<span></span>').addClass('tracker-summation-total').text(pluginData.totalFeatureCount);
        var $middle = $('<span></span>').addClass('tracker-summation-middle').text(' of ');
        // Add it all together
        $tracker.append($current).append($middle).append($total);
        // Insert into DOM
        $(pluginData.containerIDTag).append($tracker);
      }
    }

    // Update the tracker information with the new centered feature
    var updateTracker = function(oldCenter, newCenter) {
      if (options.trackerIndividual) {
        // get selectors for the two trackers
        var $trackerContainer = pluginData.featuresContainer.find(".tracker-individual-container");
        var $oldCenter = $trackerContainer.find("#tracker-"+oldCenter);
        var $newCenter = $trackerContainer.find("#tracker-"+newCenter);

        // change classes
        $oldCenter.removeClass("tracker-individual-blip-selected");
        $newCenter.addClass("tracker-individual-blip-selected");
      }
      
      if (options.trackerSummation) {
        var $trackerContainer = pluginData.featuresContainer.find('.tracker-summation-container');
        $trackerContainer.find('.tracker-summation-current').text(newCenter);
      }
    }

    var setTimer = function(stop) {
      // clear the timeout var if it exists
      clearTimeout(pluginData.timeoutVar);

      // set interval for moving if autoplay is set
      if (!stop && options.autoPlay != 0) {
        var autoTime = (Math.abs(options.autoPlay) < options.carouselSpeed) ? options.carouselSpeed : Math.abs(options.autoPlay);
        pluginData.timeoutVar = setTimeout(function () {
          (options.autoPlay > 0) ? initiateMove(true,1) : initiateMove(false,1);
        }, autoTime);
      }
    }


    var rotatePositions = function(direction) {
      $.each(pluginData.featuresArray, function () {
        var newPos;
        if (direction == false) {
          newPos = getNextNum($(this).data().position);
        } else {
          newPos = getPreviousNum($(this).data().position);
        }
        $(this).data('position',newPos);
      });
    }

    var animateFeature = function($feature, direction)
    {
      var new_width, new_height, new_top, new_left, new_zindex, new_padding, new_fade;

      // Determine the old and new positions of the feature
      var oldPosition = $feature.data('position');
      var newPosition;
      if (direction == true)
        newPosition = getPreviousNum(oldPosition);
      else
        newPosition = getNextNum(oldPosition);
        
      // callback for moving out of center pos
      if (oldPosition == 1) {
        options.leavingCenter($feature);
      }

      // Caculate new new css values depending on where the feature will be located
      if (newPosition == 1) {
        new_width = pluginData.largeFeatureWidth;
        new_height = pluginData.largeFeatureHeight;
        new_bottom = options.bottomPadding;
        new_zindex = $feature.css("z-index");
        new_left = (pluginData.containerWidth / 2) - (pluginData.largeFeatureWidth / 2) - (pluginData.borderWidth / 2);
        new_fade = 1.0;
      } else {
        new_width = pluginData.smallFeatureWidth;
        new_height = pluginData.smallFeatureHeight;
        new_bottom = options.smallFeatureOffset + options.bottomPadding;
        new_zindex = 1;
        new_fade = 1.0;
        // some info is different for the left, right, and hidden positions
        // left
        if (newPosition == pluginData.totalFeatureCount) {
          new_left = options.sidePadding;
        // right
        } else if (newPosition == 2) {
          new_left = pluginData.containerWidth - pluginData.smallFeatureWidth - options.sidePadding - pluginData.borderWidth;
        // hidden
        } else {
          new_left = (pluginData.containerWidth / 2) - (pluginData.smallFeatureWidth / 2) - (pluginData.borderWidth / 2);
          new_fade = 0;
        }
      }
      // This code block takes care of hiding the feature information if the feature is leaving the center
      if (oldPosition == 1) {
        // Slide up the story information
        $feature.find(".carousel-caption")
          .hide();
      }

      // Animate the feature div to its new location
      $feature
        .animate(
          {
            width: new_width,
            height: new_height,
            top: new_top,
            left: new_left,
            opacity: new_fade
          },
          options.carouselSpeed,
          options.animationEasing,
          function () {
            // Take feature info out of hiding if new position is center
            if (newPosition == 1) {
              // need to set the height to auto to accomodate caption if displayed below image
              if (options.captionBelow)
                $feature.css('height','auto');
              // fade in the feature information
              $feature.find(".carousel-caption")
                .fadeTo("fast",1.0);
              // callback for moved to center
              options.movedToCenter($feature);
            }
            // decrement the animation queue
            pluginData.rotationsRemaining = pluginData.rotationsRemaining - 1;
            // have to change the z-index after the animation is done
            $feature.css("z-index", new_zindex);
            // change trackers if using them
            if (options.trackerIndividual || options.trackerSummation) {
              // just update the tracker once; once the new center feature has arrived in center
              if (newPosition == 1) {
                // figure out what item was just in the center, and what item is now in the center
                var newCenterItemNum = pluginData.featuresContainer.find(".carousel-feature").index($feature) + 1;
                var oldCenterItemNum;
                if (direction == false)
                  oldCenterItemNum = getNextNum(newCenterItemNum);
                else
                  oldCenterItemNum = getPreviousNum(newCenterItemNum);
                // now update the trackers
                updateTracker(oldCenterItemNum, newCenterItemNum);
              }
            }

            // did all the the animations finish yet?
            var divide = pluginData.rotationsRemaining / pluginData.itemsToAnimate;
            if (divide % 1 == 0) {
              // if so, set moving to false...
              pluginData.currentlyMoving = false;
              // change positions for all items...
              rotatePositions(direction);

              // and move carousel again if queue is not empty
              if (pluginData.rotationsRemaining > 0)
                move(direction);
            }
            
            // reset timer and auto rotate again
            setTimer(false);
          }
        )
        // select the image within the feature
        .find('.carousel-image')
          // animate its size down
          .animate({
            width: new_width,
            height: new_height
          },
          options.carouselSpeed,
          options.animationEasing)
        .end();
    }


    var move = function(direction)
    {
      // Set the carousel to currently moving
      pluginData.currentlyMoving = true;

      var $newCenter, $newLeft, $newRight, $newHidden;
      if (direction == true) {

        $newCenter = getContainer(getNextNum(pluginData.currentCenterNum));
        $newLeft = getContainer(pluginData.currentCenterNum);
        $newRight = getContainer(getNextNum(getNextNum(pluginData.currentCenterNum)));
        $newHidden = getContainer(getPreviousNum(pluginData.currentCenterNum));
        pluginData.currentCenterNum = getNextNum(pluginData.currentCenterNum);
      } else {
        $newCenter = getContainer(getPreviousNum(pluginData.currentCenterNum));
        $newLeft = getContainer(getPreviousNum(getPreviousNum(pluginData.currentCenterNum)));
        $newRight = getContainer(pluginData.currentCenterNum);
        $newHidden = getContainer(getNextNum(pluginData.currentCenterNum));
        pluginData.currentCenterNum = getPreviousNum(pluginData.currentCenterNum);
      }

      if (direction) {
        $newLeft.css("z-index", 3);
      } else {
        $newRight.css("z-index", 3);
      }
      $newCenter.css("z-index", 4);


      animateFeature($newLeft, direction);
      animateFeature($newCenter, direction);
      animateFeature($newRight, direction);

      if (pluginData.totalFeatureCount > 3) {
        animateFeature($newHidden, direction);
      }
    }


    var initiateMove = function(direction, rotations) {
      if (pluginData.currentlyMoving == false) {
        var queue = rotations * pluginData.itemsToAnimate;
        pluginData.rotationsRemaining = queue;
        move(direction);
      }
    }


    var findShortestDistance = function(from, to) {
      var goingToLeft = 1, goingToRight = 1, tracker;
      tracker = from;
      // see how long it takes to go to the left
      while ((tracker = getPreviousNum(tracker)) != to) {
        goingToLeft++;
      }

      tracker = from;

      while ((tracker = getNextNum(tracker)) != to) {
        goingToRight++;
      }

      return (goingToLeft < goingToRight) ? goingToLeft*-1 : goingToRight;
    }

    $(options.leftButtonTag).live('click',function () {
      initiateMove(false,1);
    });

    $(options.rightButtonTag).live('click',function () {
      initiateMove(true,1);
    });

    pluginData.featuresContainer.find(".carousel-feature")
      .click(function () {
        var position = $(this).data('position');
        if (position == 2) {
          initiateMove(true,1);
        } else if (position == pluginData.totalFeatureCount) {
          initiateMove(false,1);
        }
      })
      .mouseover(function () {
        if (pluginData.currentlyMoving == false) {
          var position = $(this).data('position');
          if (position == 2 || position == pluginData.totalFeatureCount) {
            $(this).css("opacity",1);
          }
        }

        if (options.pauseOnHover) setTimer(true);

        if (options.stopOnHover) options.autoPlay = 0;
      })
      .mouseout(function () {
        if (pluginData.currentlyMoving == false) {
          var position = $(this).data('position');
          if (position == 2 || position == pluginData.totalFeatureCount) {
            $(this).css("opacity",1);
          }
        }

        if (options.pauseOnHover) {
          setTimer(false);
        }
      });

    $("a", pluginData.containerIDTag).live("click", function (event) {

      var $parents = $(this).parentsUntil(pluginData.containerIDTag);

      $parents.each(function () {
        var position = $(this).data('position');

        if (position != undefined) {

          if (position != 1) {
            if (position == pluginData.totalFeatureCount) {
              initiateMove(false,1);
            } else if (position == 2) {
              initiateMove(true,1);
            }
            event.preventDefault();
            return false;

          } else {
            options.clickedCenter($(this));
          }
        }
      });
    });


    $(".tracker-individual-blip",  pluginData.containerIDTag).live("click",function () {

      var goTo = $(this).attr("id").substring(8);

      var whereIsIt = pluginData.featuresContainer.find(".carousel-feature").eq(goTo-1).data('position');

      var currentlyAt = pluginData.currentCenterNum;
      if (goTo != currentlyAt) {

        var shortest = findShortestDistance(1, whereIsIt);

        if (shortest < 0) {
          initiateMove(false,(shortest*-1));
        } else {
          initiateMove(true,shortest);
        }
      }

    });
    

    this.initialize = function () {

      preload(function () {
        setupFeatureDimensions();
        setupCarousel();
        setupFeaturePositions();
        setupTrackers();
        initiateMove(true,1);
      });
 
      return this;
    };
    
    this.next = function() {
      initiateMove(true, 1);
    }
    this.prev = function () {
      initiateMove(false, 1);
    }
    this.pause = function () {
      setTimer(true);
    }
    this.start = function () {
      setTimer(false);
    }

    return this.initialize();
  };
  
  $.fn.featureCarousel.defaults = {

    largeFeatureWidth :   0,
    largeFeatureHeight:		0,
    smallFeatureWidth:    .5,
    smallFeatureHeight:		.5,

    bottomPadding:           20,

    sidePadding:          50,

    smallFeatureOffset:		50,

    startingFeature:      1,

    carouselSpeed:        1000,
    autoPlay:             4000,

    pauseOnHover:         true,

    stopOnHover:          false,
    trackerIndividual:    true,
    trackerSummation:     true,
    preload:              true,
    displayCutoff:        0,
    animationEasing:      'swing',
    leftButtonTag:        '#carousel-left',
    rightButtonTag:       '#carousel-right',
    captionBelow:         false,
    movedToCenter:        $.noop,
    leavingCenter:        $.noop,
    
    clickedCenter:        $.noop
  };
})(jQuery);