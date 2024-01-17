package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlayListService;
import com.example.demo.services.SongService;

@Controller
public class PlayListController {
	@Autowired
	SongService songService;
	@Autowired
	PlayListService playlistService;
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model)
	{
		List<Song> songList=songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlaylist";
	}
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		playlistService.addPlaylist(playlist);
		List<Song> songList=playlist.getSong();
		for(Song s:songList)
		{
			s.getPlaylists().add(playlist);
			songService.updateSong(s);
		}

		return "admin";
	}
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model)
	{
		List<Playlist> allPlaylists=playlistService.fetchAllPlaylist();
		model.addAttribute(" allPlaylists", allPlaylists);
		return "displayPlaylists";
		
	}
	

}
