package com.cesco.fs.consulting.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultingFileInfo implements Serializable{
  private static final long serialVersionUID = -2652142427983006421L;
  private String name;
  private String orgName;
  private String expname;


}
