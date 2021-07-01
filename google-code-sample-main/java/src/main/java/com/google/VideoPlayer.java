package com.google;


import java.util.*;
import java.util.stream.Collectors;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  ArrayList<String> arr=new ArrayList<String>();

  HashMap<String,Video> map=new HashMap<>();

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public String getVideoString(Video video)
  {
    String tags=String.join(" ",video.getTags());
    return video.getTitle()+" ("+video.getVideoId()+") ["+tags+"]";
  }
  public void showAllVideos() {

    List<Video>  videos= videoLibrary.getVideos();

    videos.sort(Comparator.comparing(Video::getTitle));

    System.out.println("Here's a list of all available videos:");
   for(Video video: videos)
   {
     System.out.println("/t" + getVideoString(video));
   }

  }

  public void playVideo(String videoId) {
    flag=false;
    Video video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot play video: Video does not exist");
      return;
    }
    if(arr.size()>0)
    {

          System.out.println("Stopping video: " + arr.get(0));
          arr.remove(0);
          arr.add(videoLibrary.getVideo(videoId).getTitle());
          System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());


    } else {

        arr.add(videoLibrary.getVideo(videoId).getTitle());
        System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
      }

    }



  public void stopVideo() {

    String videoId = videoLibrary.getVideos().get(0).getVideoId();
    Video video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot play video: Video does not exist");
    }
    else if(arr.size() > 0) {
      System.out.println("Stopping video: " + arr.get(0));
      arr.remove(0);
    }else {
      System.out.println("Cannot stop video: No video is currently playing");

    }
  }

  public void playRandomVideo() {

    List<Video> vids= videoLibrary.getVideos();
    ArrayList<String> ID = new ArrayList<String>();

    for(Video vid: vids) {
      ID.add(((Video) vid).getVideoId());
    }

    Random rand = new Random();
    String randomElement = ID.get(rand.nextInt(ID.size()));
    playVideo(randomElement);

  }

  int count=0;boolean flag=false;
  public void pauseVideo() {

    if (arr.size()>0) {
      if (!flag) {
        flag = true;
        System.out.println("Pausing video: " + arr.get(0));
      } else if (flag) {
        System.out.println("Video already paused: " + arr.get(0));
      }
    } else {
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }
//    if((arr.size()>0)&&(count>0))
//    {
//      System.out.println("Video already paused: "+arr.get(0));
//      return;
//    }
//    if(arr.size()>0)
//    {
//      System.out.println("Pausing video: "+arr.get(0));
//      count++;
//      flag=true;
//      return;
   // }

  //}

  public void continueVideo() {
    if (arr.size()>0) {
      if (flag) {
        System.out.println("Continuing video: " + arr.get(0));
        flag = false;
      } else if (!flag) {
        System.out.println("Cannot continue video: Video is not paused");
      }

    } else {
      System.out.println("Cannot continue video: No video is currently playing");
    }
  }


  public void showPlaying()
  {

    if(arr.size()>0)
    {
      List<Video> videos= videoLibrary.getVideos();
      for(Video video:videos)
      {
        if(video.getTitle()== arr.get(0))
        {
          String pauseStatus;
          if(flag){
            pauseStatus = " - PAUSED";
          } else{
            pauseStatus = "";
          }
          System.out.println("Currently playing: " + video.getTitle() + " (" + video.getVideoId() + ") [" + video.getTags().stream().collect(Collectors.joining(" ")) + "]"+ pauseStatus);
        }
        }
      }
       else{
      System.out.print("No video is currently playing");
    }

//          if(video.getTitle()==arr.get(0)&&flag==true) {
//            System.out.println("Currently playing: " + video.getTitle() + " (" + video.getVideoId() + ") [" + video.getTags().stream().collect(Collectors.joining(" ")) + "]"+ " - PAUSED");
//            return;
//          }
//          if(video.getTitle()==arr.get(0)&&flag==false) {
//            System.out.print("Currently playing: ");
//
//            System.out.println( getVideoString(video)+"/t");
//          }
        }




  public void createPlaylist(String playlistName) {
    boolean match = false;
    for (Map.Entry m : map.entrySet()) {
      if (playlistName.equalsIgnoreCase(m.getKey().toString())) {
        System.out.println("Cannot create playlist: A playlist with the same name already exists");
        match=true;
      }
    }
    if(!match)
    {
      map.put(playlistName,null);
      System.out.println("Successfully created new playlist: "+playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    List<Video> videos = videoLibrary.getVideos();
    boolean videoExists = false;
    if (!map.containsKey(playlistName)) {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      return;
    }
    for (Video video : videos) {
      if (video.getVideoId() == videoId) {
        videoExists = true;
      }
    }
    if (!videoExists) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      return;
    }
    boolean added = false;

    for (Video video : videos) {
      if (video.getVideoId() == videoId) {
        map.putIfAbsent(playlistName, video);
        added = true;
        System.out.println("Added video to the playlist successfully: " + playlistName);
        return;
      }
    }
    for (Map.Entry m : map.entrySet()) {
      if (map.get(playlistName) != null) {
        System.out.println("Cannot add video to " + playlistName + ": Video already added");
        return;
      }
    }
  }
  public void showAllPlaylists() {
    if (map.size() > 0) {
      System.out.println("Showing all playlists:");
      for (Map.Entry m : map.entrySet()) {
        System.out.println(m.getKey());
      }
    } else {
      System.out.println("No playlists exist yet");
    }
  }

  public void showPlaylist(String playlistName) {
    if(!map.containsKey(playlistName))
    {
      System.out.println("Cannot show playlist "+playlistName+": Playlist does not exist");
      return;
    }
    System.out.println("Showing playlist: " + playlistName);
    boolean p = false;
    for (Map.Entry m : map.entrySet()) {
      if (playlistName.equalsIgnoreCase(m.getKey().toString())) {
        Video video = map.get(playlistName);
        if (video != null) {
          p = true;
          System.out.println(video.getTitle() + " (" + video.getVideoId() + ") [" + video.getTags().stream().collect(Collectors.joining(" ")) + "]");
          return;
        }
      }
    }
    if (!p) {
      System.out.println("No videos here yet");
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}
