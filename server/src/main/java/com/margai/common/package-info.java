/**
 * common module (TECH_PLAN §1.3): error envelope, request id, IST clock, idempotency,
 * pagination, config binding, message catalogs — and the value types that more than one module
 * stores, because feature modules may not depend on each other for them (§1.4). Owns
 * {@code idempotency_keys} (D28). Depends on nothing.
 */
@org.springframework.modulith.ApplicationModule(displayName = "common", allowedDependencies = {})
package com.margai.common;
