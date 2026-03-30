package com.cesco.sys.comm.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NiceIdEncDataParamDto implements Serializable{
   
   private static final long serialVersionUID = 2236914261683167007L;
   
   private String successUrl;
   private String failUrl;
   
   
 }