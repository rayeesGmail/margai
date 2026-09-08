package com.margai.auth.internal;

import com.margai.common.api.Language;
import java.time.Duration;

/**
 * One code on its way to a student: the channel, the normalised destination, the code in the
 * clear (its only appearance outside the device), the language the copy is rendered in (§3.8)
 * and how long the code lives, for the message text.
 */
public record OtpDelivery(OtpChannel channel, String destination, String code, Language language, Duration ttl) {
}
