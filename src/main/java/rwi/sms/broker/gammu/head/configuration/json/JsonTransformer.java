/*
 * Copyright (c) 2018. Robert Wittek <oe1rxw@gmail.com>
 *
 * This software may be modified and distributed under the terms of the MIT license.  See the LICENSE file for details.
 */

package rwi.sms.broker.gammu.head.configuration.json;

import com.google.gson.Gson;
import spark.ResponseTransformer;

import javax.inject.Inject;

public class JsonTransformer implements ResponseTransformer {
    private final Gson gson;

    @Inject
    public JsonTransformer(final Gson gson) {
        this.gson = gson;
    }

    @Override
    public String render(final Object o) {
        return gson.toJson(o);
    }
}
