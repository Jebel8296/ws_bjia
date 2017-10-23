/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.chinamcom.framework.monitor.model;

import io.vertx.core.json.JsonObject;

/**
 * Converter for {@link com.chinamcom.framework.monitor.model.BackstageUser}.
 *
 * NOTE: This class has been automatically generated from the {@link com.chinamcom.framework.monitor.model.BackstageUser} original class using Vert.x codegen.
 */
public class BackstageUserConverter {

  public static void fromJson(JsonObject json, BackstageUser obj) {
    if (json.getValue("email") instanceof String) {
      obj.setEmail((String)json.getValue("email"));
    }
    if (json.getValue("headPortrait") instanceof String) {
      obj.setHeadPortrait((String)json.getValue("headPortrait"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("nickname") instanceof String) {
      obj.setNickname((String)json.getValue("nickname"));
    }
    if (json.getValue("password") instanceof String) {
      obj.setPassword((String)json.getValue("password"));
    }
    if (json.getValue("phone") instanceof String) {
      obj.setPhone((String)json.getValue("phone"));
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("username") instanceof String) {
      obj.setUsername((String)json.getValue("username"));
    }
  }

  public static void toJson(BackstageUser obj, JsonObject json) {
    if (obj.getEmail() != null) {
      json.put("email", obj.getEmail());
    }
    if (obj.getHeadPortrait() != null) {
      json.put("headPortrait", obj.getHeadPortrait());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getNickname() != null) {
      json.put("nickname", obj.getNickname());
    }
    if (obj.getPassword() != null) {
      json.put("password", obj.getPassword());
    }
    if (obj.getPhone() != null) {
      json.put("phone", obj.getPhone());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getUsername() != null) {
      json.put("username", obj.getUsername());
    }
  }
}