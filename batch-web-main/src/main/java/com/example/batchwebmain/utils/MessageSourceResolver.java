package com.example.batchwebmain.utils;

import lombok.Builder;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Builder
@SuppressWarnings("serial")
public class MessageSourceResolver implements MessageSourceResolvable, Serializable {

    private final String[] codes;

    @org.springframework.lang.Nullable
    private String defaultMessage;

    @org.springframework.lang.Nullable
    private Object[] arguments;

    private MessageSourceResolver(
            final String[] codes, final @org.springframework.lang.Nullable String defaultMessage, @org.springframework.lang.Nullable Object[] arguments) {
        this.codes = codes;
        this.defaultMessage = defaultMessage;
        this.arguments = arguments;
    }

    //  public static MessageSourceResolver of(final String code) {
    //    return new MessageSourceResolver(new String[] {code}, null, null);
    //  }

    public static MessageSourceResolver of(final String defaultMessage) {
        return new MessageSourceResolver(null, defaultMessage, null);
    }

    public static MessageSourceResolver of(final String[] codes) {
        return new MessageSourceResolver(codes, null, null);
    }

    public static MessageSourceResolver of(final String code, final Object[] arguments) {
        return new MessageSourceResolver(new String[] {code}, null, arguments);
    }

    public static MessageSourceResolver of(final String[] codes, final Object[] arguments) {
        return new MessageSourceResolver(codes, null, arguments);
    }

    public static MessageSourceResolver of(final String[] codes, final String defaultMessage) {
        return new MessageSourceResolver(codes, defaultMessage, null);
    }

    public static MessageSourceResolver of(final String code, final String defaultMessage) {
        return new MessageSourceResolver(new String[] {code}, defaultMessage, null);
    }

    public static MessageSourceResolver of(
            final String[] codes, final String defaultMessage, final Object[] arguments) {
        return new MessageSourceResolver(codes, defaultMessage, arguments);
    }

    public static MessageSourceResolver of(
            final String code, final String defaultMessage, final Object... arguments) {
        return new MessageSourceResolver(new String[] {code}, defaultMessage, arguments);
    }

    public static MessageSourceResolver of(final MessageResolver resolver) {
        return of(resolver.messageCode(), resolver.defaultMessage());
    }

    public static MessageSourceResolver of(final MessageSourceResolvable resolvable) {
        return of(resolvable.getCodes(), resolvable.getDefaultMessage(), resolvable.getArguments());
    }

    /** Return the default code of this resolvable, that is, the last one in the codes array. */
    @org.springframework.lang.Nullable
    public String getCode() {
        return (this.codes != null && this.codes.length > 0 ? this.codes[this.codes.length - 1] : null);
    }

    @Override
    @org.springframework.lang.Nullable
    public String[] getCodes() {
        return this.codes;
    }

    @Override
    @org.springframework.lang.Nullable
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    @org.springframework.lang.Nullable
    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    public boolean shouldRenderDefaultMessage() {
        return true;
    }

    protected final String resolvableToString() {
        String result =
                "codes ["
                        + StringUtils.arrayToDelimitedString(this.codes, ",")
                        + "]; arguments ["
                        + StringUtils.arrayToDelimitedString(this.arguments, ",")
                        + "]; default message ["
                        + this.defaultMessage
                        + ']';
        return result;
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + resolvableToString();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MessageSourceResolvable otherResolvable)) {
            return false;
        }
        return (ObjectUtils.nullSafeEquals(getCodes(), otherResolvable.getCodes())
                && ObjectUtils.nullSafeEquals(getArguments(), otherResolvable.getArguments())
                && ObjectUtils.nullSafeEquals(getDefaultMessage(), otherResolvable.getDefaultMessage()));
    }

    @Override
    public int hashCode() {
        int hashCode = ObjectUtils.nullSafeHashCode(getCodes());
        hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(getArguments());
        hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(getDefaultMessage());
        return hashCode;
    }
}
