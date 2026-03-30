package com.cesco.sys.comm.dto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtSessionInfoDto implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -9037586113294229834L;
  private static final Logger logger = LogManager.getLogger(CmmtSessionInfoDto.class);
	private String sessionId;
	private String sessionValue;
	private Date expiredDt;
	private Date createdDt;
  private static final ObjectMapper mapper = new ObjectMapper();

	@JsonIgnore
	public <T> T getSessionJsonValue(Class<T> cls) {
		if (sessionValue.isBlank()) {
			return null;
		}
		return toObject(sessionValue, cls);
	}

  public static <T> T toObject(String json, Class<T> cls) {
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    try {
        return mapper.readValue(json, cls);
    } catch (IOException e) {
        logger.error(exception.getStackTraceString(e));
        return null;
    }

  }
  public static class exception {
    public static String getStackTraceString(Throwable e) {
        try (StringWriter sw = new StringWriter();) {
          e.printStackTrace(new PrintWriter(sw));
          return sw.toString();
        } catch (IOException e1) {
            logger.error("error:" + e1.getMessage());
            return null;
        }
    }
  }

}
