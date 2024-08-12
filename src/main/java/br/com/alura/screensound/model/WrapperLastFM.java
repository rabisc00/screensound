package br.com.alura.screensound.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WrapperLastFM(@JsonAlias("artist") ArtistLastFM artist) {
}
