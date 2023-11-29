package com.bestbenefits.takoyaki.config.apiresponse;

import lombok.Getter;

public record ApiResponse<D>(boolean success, D data, int status) { }
