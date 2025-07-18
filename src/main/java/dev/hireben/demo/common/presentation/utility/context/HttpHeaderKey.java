package dev.hireben.demo.common.presentation.utility.context;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpHeaderKey {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  public final String API_KEY = "X-Api-Key";
  public final String TRACE_ID = "X-Trace-Id";
  public final String AUTH_TOKEN = "Authorization";

}
