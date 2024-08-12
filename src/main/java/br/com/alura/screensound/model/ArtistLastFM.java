package br.com.alura.screensound.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistLastFM(@JsonAlias("bio") BioLastFM bio) {
}
