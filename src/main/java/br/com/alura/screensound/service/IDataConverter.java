package br.com.alura.screensound.service;

public interface IDataConverter {
    <T> T convertTo(String json, Class<T> convertToClass);
}
