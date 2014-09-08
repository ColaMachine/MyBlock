package cola.machine.game.myblocks.protobuf;


public class ChunksProtobuf {
	  private ChunksProtobuf() {}
	  public static void registerAllExtensions(
	      com.google.protobuf.ExtensionRegistry registry) {
	  }
	  /**
	   * Protobuf enum {@code Type}
	   */
	  public enum Type
	      implements com.google.protobuf.ProtocolMessageEnum {
	    /**
	     * <code>Unknown = 0;</code>
	     */
	    Unknown(0, 0),
	    /**
	     * <code>DenseArray4Bit = 1;</code>
	     */
	    DenseArray4Bit(1, 1),
	    /**
	     * <code>DenseArray8Bit = 2;</code>
	     */
	    DenseArray8Bit(2, 2),
	    /**
	     * <code>DenseArray16Bit = 3;</code>
	     */
	    DenseArray16Bit(3, 3),
	    /**
	     * <code>SparseArray4Bit = 4;</code>
	     */
	    SparseArray4Bit(4, 4),
	    /**
	     * <code>SparseArray8Bit = 5;</code>
	     */
	    SparseArray8Bit(5, 5),
	    /**
	     * <code>SparseArray16Bit = 6;</code>
	     */
	    SparseArray16Bit(6, 6),
	    ;

	    /**
	     * <code>Unknown = 0;</code>
	     */
	    public static final int Unknown_VALUE = 0;
	    /**
	     * <code>DenseArray4Bit = 1;</code>
	     */
	    public static final int DenseArray4Bit_VALUE = 1;
	    /**
	     * <code>DenseArray8Bit = 2;</code>
	     */
	    public static final int DenseArray8Bit_VALUE = 2;
	    /**
	     * <code>DenseArray16Bit = 3;</code>
	     */
	    public static final int DenseArray16Bit_VALUE = 3;
	    /**
	     * <code>SparseArray4Bit = 4;</code>
	     */
	    public static final int SparseArray4Bit_VALUE = 4;
	    /**
	     * <code>SparseArray8Bit = 5;</code>
	     */
	    public static final int SparseArray8Bit_VALUE = 5;
	    /**
	     * <code>SparseArray16Bit = 6;</code>
	     */
	    public static final int SparseArray16Bit_VALUE = 6;


	    public final int getNumber() { return value; }

	    public static Type valueOf(int value) {
	      switch (value) {
	        case 0: return Unknown;
	        case 1: return DenseArray4Bit;
	        case 2: return DenseArray8Bit;
	        case 3: return DenseArray16Bit;
	        case 4: return SparseArray4Bit;
	        case 5: return SparseArray8Bit;
	        case 6: return SparseArray16Bit;
	        default: return null;
	      }
	    }

	    public static com.google.protobuf.Internal.EnumLiteMap<Type>
	        internalGetValueMap() {
	      return internalValueMap;
	    }
	    private static com.google.protobuf.Internal.EnumLiteMap<Type>
	        internalValueMap =
	          new com.google.protobuf.Internal.EnumLiteMap<Type>() {
	            public Type findValueByNumber(int number) {
	              return Type.valueOf(number);
	            }
	          };

	    public final com.google.protobuf.Descriptors.EnumValueDescriptor
	        getValueDescriptor() {
	      return getDescriptor().getValues().get(index);
	    }
	    public final com.google.protobuf.Descriptors.EnumDescriptor
	        getDescriptorForType() {
	      return getDescriptor();
	    }
	    public static final com.google.protobuf.Descriptors.EnumDescriptor
	        getDescriptor() {
	      return ChunksProtobuf.getDescriptor().getEnumTypes().get(0);
	    }

	    private static final Type[] VALUES = values();

	    public static Type valueOf(
	        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
	      if (desc.getType() != getDescriptor()) {
	        throw new java.lang.IllegalArgumentException(
	          "EnumValueDescriptor is not for this type.");
	      }
	      return VALUES[desc.getIndex()];
	    }

	    private final int index;
	    private final int value;

	    private Type(int index, int value) {
	      this.index = index;
	      this.value = value;
	    }

	    // @@protoc_insertion_point(enum_scope:Type)
	  }

	  public interface TeraArrayOrBuilder extends
	      com.google.protobuf.GeneratedMessage.
	          ExtendableMessageOrBuilder<TeraArray> {

	    // optional .Type type = 1;
	    /**
	     * <code>optional .Type type = 1;</code>
	     */
	    boolean hasType();
	    /**
	     * <code>optional .Type type = 1;</code>
	     */
	    ChunksProtobuf.Type getType();

	    // optional string class_name = 2;
	    /**
	     * <code>optional string class_name = 2;</code>
	     */
	    boolean hasClassName();
	    /**
	     * <code>optional string class_name = 2;</code>
	     */
	    java.lang.String getClassName();
	    /**
	     * <code>optional string class_name = 2;</code>
	     */
	    com.google.protobuf.ByteString
	        getClassNameBytes();

	    // optional bytes data = 3;
	    /**
	     * <code>optional bytes data = 3;</code>
	     */
	    boolean hasData();
	    /**
	     * <code>optional bytes data = 3;</code>
	     */
	    com.google.protobuf.ByteString getData();
	  }
	  /**
	   * Protobuf type {@code TeraArray}
	   */
	  public static final class TeraArray extends
	      com.google.protobuf.GeneratedMessage.ExtendableMessage<
	        TeraArray> implements TeraArrayOrBuilder {
	    // Use TeraArray.newBuilder() to construct.
	    private TeraArray(com.google.protobuf.GeneratedMessage.ExtendableBuilder<ChunksProtobuf.TeraArray, ?> builder) {
	      super(builder);
	      this.unknownFields = builder.getUnknownFields();
	    }
	    private TeraArray(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

	    private static final TeraArray defaultInstance;
	    public static TeraArray getDefaultInstance() {
	      return defaultInstance;
	    }

	    public TeraArray getDefaultInstanceForType() {
	      return defaultInstance;
	    }

	    private final com.google.protobuf.UnknownFieldSet unknownFields;
	    @java.lang.Override
	    public final com.google.protobuf.UnknownFieldSet
	        getUnknownFields() {
	      return this.unknownFields;
	    }
	    private TeraArray(
	        com.google.protobuf.CodedInputStream input,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      initFields();
	      int mutable_bitField0_ = 0;
	      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
	          com.google.protobuf.UnknownFieldSet.newBuilder();
	      try {
	        boolean done = false;
	        while (!done) {
	          int tag = input.readTag();
	          switch (tag) {
	            case 0:
	              done = true;
	              break;
	            default: {
	              if (!parseUnknownField(input, unknownFields,
	                                     extensionRegistry, tag)) {
	                done = true;
	              }
	              break;
	            }
	            case 8: {
	              int rawValue = input.readEnum();
	              ChunksProtobuf.Type value = ChunksProtobuf.Type.valueOf(rawValue);
	              if (value == null) {
	                unknownFields.mergeVarintField(1, rawValue);
	              } else {
	                bitField0_ |= 0x00000001;
	                type_ = value;
	              }
	              break;
	            }
	            case 18: {
	              bitField0_ |= 0x00000002;
	              className_ = input.readBytes();
	              break;
	            }
	            case 26: {
	              bitField0_ |= 0x00000004;
	              data_ = input.readBytes();
	              break;
	            }
	          }
	        }
	      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
	        throw e.setUnfinishedMessage(this);
	      } catch (java.io.IOException e) {
	        throw new com.google.protobuf.InvalidProtocolBufferException(
	            e.getMessage()).setUnfinishedMessage(this);
	      } finally {
	        this.unknownFields = unknownFields.build();
	        makeExtensionsImmutable();
	      }
	    }
	    public static final com.google.protobuf.Descriptors.Descriptor
	        getDescriptor() {
	      return ChunksProtobuf.internal_static_TeraArray_descriptor;
	    }

	    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
	        internalGetFieldAccessorTable() {
	      return ChunksProtobuf.internal_static_TeraArray_fieldAccessorTable
	          .ensureFieldAccessorsInitialized(
	              ChunksProtobuf.TeraArray.class, ChunksProtobuf.TeraArray.Builder.class);
	    }

	    public static com.google.protobuf.Parser<TeraArray> PARSER =
	        new com.google.protobuf.AbstractParser<TeraArray>() {
	      public TeraArray parsePartialFrom(
	          com.google.protobuf.CodedInputStream input,
	          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	          throws com.google.protobuf.InvalidProtocolBufferException {
	        return new TeraArray(input, extensionRegistry);
	      }
	    };

	    @java.lang.Override
	    public com.google.protobuf.Parser<TeraArray> getParserForType() {
	      return PARSER;
	    }

	    private int bitField0_;
	    // optional .Type type = 1;
	    public static final int TYPE_FIELD_NUMBER = 1;
	    private ChunksProtobuf.Type type_;
	    /**
	     * <code>optional .Type type = 1;</code>
	     */
	    public boolean hasType() {
	      return ((bitField0_ & 0x00000001) == 0x00000001);
	    }
	    /**
	     * <code>optional .Type type = 1;</code>
	     */
	    public ChunksProtobuf.Type getType() {
	      return type_;
	    }

	    // optional string class_name = 2;
	    public static final int CLASS_NAME_FIELD_NUMBER = 2;
	    private java.lang.Object className_;
	    /**
	     * <code>optional string class_name = 2;</code>
	     */
	    public boolean hasClassName() {
	      return ((bitField0_ & 0x00000002) == 0x00000002);
	    }
	    /**
	     * <code>optional string class_name = 2;</code>
	     */
	    public java.lang.String getClassName() {
	      java.lang.Object ref = className_;
	      if (ref instanceof java.lang.String) {
	        return (java.lang.String) ref;
	      } else {
	        com.google.protobuf.ByteString bs = 
	            (com.google.protobuf.ByteString) ref;
	        java.lang.String s = bs.toStringUtf8();
	        if (bs.isValidUtf8()) {
	          className_ = s;
	        }
	        return s;
	      }
	    }
	    /**
	     * <code>optional string class_name = 2;</code>
	     */
	    public com.google.protobuf.ByteString
	        getClassNameBytes() {
	      java.lang.Object ref = className_;
	      if (ref instanceof java.lang.String) {
	        com.google.protobuf.ByteString b = 
	            com.google.protobuf.ByteString.copyFromUtf8(
	                (java.lang.String) ref);
	        className_ = b;
	        return b;
	      } else {
	        return (com.google.protobuf.ByteString) ref;
	      }
	    }

	    // optional bytes data = 3;
	    public static final int DATA_FIELD_NUMBER = 3;
	    private com.google.protobuf.ByteString data_;
	    /**
	     * <code>optional bytes data = 3;</code>
	     */
	    public boolean hasData() {
	      return ((bitField0_ & 0x00000004) == 0x00000004);
	    }
	    /**
	     * <code>optional bytes data = 3;</code>
	     */
	    public com.google.protobuf.ByteString getData() {
	      return data_;
	    }

	    private void initFields() {
	      type_ = ChunksProtobuf.Type.Unknown;
	      className_ = "";
	      data_ = com.google.protobuf.ByteString.EMPTY;
	    }
	    private byte memoizedIsInitialized = -1;
	    public final boolean isInitialized() {
	      byte isInitialized = memoizedIsInitialized;
	      if (isInitialized != -1) return isInitialized == 1;

	      if (!extensionsAreInitialized()) {
	        memoizedIsInitialized = 0;
	        return false;
	      }
	      memoizedIsInitialized = 1;
	      return true;
	    }

	    public void writeTo(com.google.protobuf.CodedOutputStream output)
	                        throws java.io.IOException {
	      getSerializedSize();
	      com.google.protobuf.GeneratedMessage
	        .ExtendableMessage<ChunksProtobuf.TeraArray>.ExtensionWriter extensionWriter =
	          newExtensionWriter();
	      if (((bitField0_ & 0x00000001) == 0x00000001)) {
	        output.writeEnum(1, type_.getNumber());
	      }
	      if (((bitField0_ & 0x00000002) == 0x00000002)) {
	        output.writeBytes(2, getClassNameBytes());
	      }
	      if (((bitField0_ & 0x00000004) == 0x00000004)) {
	        output.writeBytes(3, data_);
	      }
	      extensionWriter.writeUntil(536870912, output);
	      getUnknownFields().writeTo(output);
	    }

	    private int memoizedSerializedSize = -1;
	    public int getSerializedSize() {
	      int size = memoizedSerializedSize;
	      if (size != -1) return size;

	      size = 0;
	      if (((bitField0_ & 0x00000001) == 0x00000001)) {
	        size += com.google.protobuf.CodedOutputStream
	          .computeEnumSize(1, type_.getNumber());
	      }
	      if (((bitField0_ & 0x00000002) == 0x00000002)) {
	        size += com.google.protobuf.CodedOutputStream
	          .computeBytesSize(2, getClassNameBytes());
	      }
	      if (((bitField0_ & 0x00000004) == 0x00000004)) {
	        size += com.google.protobuf.CodedOutputStream
	          .computeBytesSize(3, data_);
	      }
	      size += extensionsSerializedSize();
	      size += getUnknownFields().getSerializedSize();
	      memoizedSerializedSize = size;
	      return size;
	    }

	    private static final long serialVersionUID = 0L;
	    @java.lang.Override
	    protected java.lang.Object writeReplace()
	        throws java.io.ObjectStreamException {
	      return super.writeReplace();
	    }

	    public static ChunksProtobuf.TeraArray parseFrom(
	        com.google.protobuf.ByteString data)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      return PARSER.parseFrom(data);
	    }
	    public static ChunksProtobuf.TeraArray parseFrom(
	        com.google.protobuf.ByteString data,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      return PARSER.parseFrom(data, extensionRegistry);
	    }
	    public static ChunksProtobuf.TeraArray parseFrom(byte[] data)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      return PARSER.parseFrom(data);
	    }
	    public static ChunksProtobuf.TeraArray parseFrom(
	        byte[] data,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      return PARSER.parseFrom(data, extensionRegistry);
	    }
	    public static ChunksProtobuf.TeraArray parseFrom(java.io.InputStream input)
	        throws java.io.IOException {
	      return PARSER.parseFrom(input);
	    }
	    public static ChunksProtobuf.TeraArray parseFrom(
	        java.io.InputStream input,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws java.io.IOException {
	      return PARSER.parseFrom(input, extensionRegistry);
	    }
	    public static ChunksProtobuf.TeraArray parseDelimitedFrom(java.io.InputStream input)
	        throws java.io.IOException {
	      return PARSER.parseDelimitedFrom(input);
	    }
	    public static ChunksProtobuf.TeraArray parseDelimitedFrom(
	        java.io.InputStream input,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws java.io.IOException {
	      return PARSER.parseDelimitedFrom(input, extensionRegistry);
	    }
	    public static ChunksProtobuf.TeraArray parseFrom(
	        com.google.protobuf.CodedInputStream input)
	        throws java.io.IOException {
	      return PARSER.parseFrom(input);
	    }
	    public static ChunksProtobuf.TeraArray parseFrom(
	        com.google.protobuf.CodedInputStream input,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws java.io.IOException {
	      return PARSER.parseFrom(input, extensionRegistry);
	    }

	    public static Builder newBuilder() { return Builder.create(); }
	    public Builder newBuilderForType() { return newBuilder(); }
	    public static Builder newBuilder(ChunksProtobuf.TeraArray prototype) {
	      return newBuilder().mergeFrom(prototype);
	    }
	    public Builder toBuilder() { return newBuilder(this); }

	    @java.lang.Override
	    protected Builder newBuilderForType(
	        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
	      Builder builder = new Builder(parent);
	      return builder;
	    }
	    /**
	     * Protobuf type {@code TeraArray}
	     */
	    public static final class Builder extends
	        com.google.protobuf.GeneratedMessage.ExtendableBuilder<
	          ChunksProtobuf.TeraArray, Builder> implements ChunksProtobuf.TeraArrayOrBuilder {
	      public static final com.google.protobuf.Descriptors.Descriptor
	          getDescriptor() {
	        return ChunksProtobuf.internal_static_TeraArray_descriptor;
	      }

	      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
	          internalGetFieldAccessorTable() {
	        return ChunksProtobuf.internal_static_TeraArray_fieldAccessorTable
	            .ensureFieldAccessorsInitialized(
	                ChunksProtobuf.TeraArray.class, ChunksProtobuf.TeraArray.Builder.class);
	      }

	      // Construct using ChunksProtobuf.TeraArray.newBuilder()
	      private Builder() {
	        maybeForceBuilderInitialization();
	      }

	      private Builder(
	          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
	        super(parent);
	        maybeForceBuilderInitialization();
	      }
	      private void maybeForceBuilderInitialization() {
	        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
	        }
	      }
	      private static Builder create() {
	        return new Builder();
	      }

	      public Builder clear() {
	        super.clear();
	        type_ = ChunksProtobuf.Type.Unknown;
	        bitField0_ = (bitField0_ & ~0x00000001);
	        className_ = "";
	        bitField0_ = (bitField0_ & ~0x00000002);
	        data_ = com.google.protobuf.ByteString.EMPTY;
	        bitField0_ = (bitField0_ & ~0x00000004);
	        return this;
	      }

	      public Builder clone() {
	        return create().mergeFrom(buildPartial());
	      }

	      public com.google.protobuf.Descriptors.Descriptor
	          getDescriptorForType() {
	        return ChunksProtobuf.internal_static_TeraArray_descriptor;
	      }

	      public ChunksProtobuf.TeraArray getDefaultInstanceForType() {
	        return ChunksProtobuf.TeraArray.getDefaultInstance();
	      }

	      public ChunksProtobuf.TeraArray build() {
	        ChunksProtobuf.TeraArray result = buildPartial();
	        if (!result.isInitialized()) {
	          throw newUninitializedMessageException(result);
	        }
	        return result;
	      }

	      public ChunksProtobuf.TeraArray buildPartial() {
	        ChunksProtobuf.TeraArray result = new ChunksProtobuf.TeraArray(this);
	        int from_bitField0_ = bitField0_;
	        int to_bitField0_ = 0;
	        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
	          to_bitField0_ |= 0x00000001;
	        }
	        result.type_ = type_;
	        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
	          to_bitField0_ |= 0x00000002;
	        }
	        result.className_ = className_;
	        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
	          to_bitField0_ |= 0x00000004;
	        }
	        result.data_ = data_;
	        result.bitField0_ = to_bitField0_;
	        onBuilt();
	        return result;
	      }

	      public Builder mergeFrom(com.google.protobuf.Message other) {
	        if (other instanceof ChunksProtobuf.TeraArray) {
	          return mergeFrom((ChunksProtobuf.TeraArray)other);
	        } else {
	          super.mergeFrom(other);
	          return this;
	        }
	      }

	      public Builder mergeFrom(ChunksProtobuf.TeraArray other) {
	        if (other == ChunksProtobuf.TeraArray.getDefaultInstance()) return this;
	        if (other.hasType()) {
	          setType(other.getType());
	        }
	        if (other.hasClassName()) {
	          bitField0_ |= 0x00000002;
	          className_ = other.className_;
	          onChanged();
	        }
	        if (other.hasData()) {
	          setData(other.getData());
	        }
	        this.mergeExtensionFields(other);
	        this.mergeUnknownFields(other.getUnknownFields());
	        return this;
	      }

	      public final boolean isInitialized() {
	        if (!extensionsAreInitialized()) {
	          
	          return false;
	        }
	        return true;
	      }

	      public Builder mergeFrom(
	          com.google.protobuf.CodedInputStream input,
	          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	          throws java.io.IOException {
	        ChunksProtobuf.TeraArray parsedMessage = null;
	        try {
	          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
	        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
	          parsedMessage = (ChunksProtobuf.TeraArray) e.getUnfinishedMessage();
	          throw e;
	        } finally {
	          if (parsedMessage != null) {
	            mergeFrom(parsedMessage);
	          }
	        }
	        return this;
	      }
	      private int bitField0_;

	      // optional .Type type = 1;
	      private ChunksProtobuf.Type type_ = ChunksProtobuf.Type.Unknown;
	      /**
	       * <code>optional .Type type = 1;</code>
	       */
	      public boolean hasType() {
	        return ((bitField0_ & 0x00000001) == 0x00000001);
	      }
	      /**
	       * <code>optional .Type type = 1;</code>
	       */
	      public ChunksProtobuf.Type getType() {
	        return type_;
	      }
	      /**
	       * <code>optional .Type type = 1;</code>
	       */
	      public Builder setType(ChunksProtobuf.Type value) {
	        if (value == null) {
	          throw new NullPointerException();
	        }
	        bitField0_ |= 0x00000001;
	        type_ = value;
	        onChanged();
	        return this;
	      }
	      /**
	       * <code>optional .Type type = 1;</code>
	       */
	      public Builder clearType() {
	        bitField0_ = (bitField0_ & ~0x00000001);
	        type_ = ChunksProtobuf.Type.Unknown;
	        onChanged();
	        return this;
	      }

	      // optional string class_name = 2;
	      private java.lang.Object className_ = "";
	      /**
	       * <code>optional string class_name = 2;</code>
	       */
	      public boolean hasClassName() {
	        return ((bitField0_ & 0x00000002) == 0x00000002);
	      }
	      /**
	       * <code>optional string class_name = 2;</code>
	       */
	      public java.lang.String getClassName() {
	        java.lang.Object ref = className_;
	        if (!(ref instanceof java.lang.String)) {
	          java.lang.String s = ((com.google.protobuf.ByteString) ref)
	              .toStringUtf8();
	          className_ = s;
	          return s;
	        } else {
	          return (java.lang.String) ref;
	        }
	      }
	      /**
	       * <code>optional string class_name = 2;</code>
	       */
	      public com.google.protobuf.ByteString
	          getClassNameBytes() {
	        java.lang.Object ref = className_;
	        if (ref instanceof String) {
	          com.google.protobuf.ByteString b = 
	              com.google.protobuf.ByteString.copyFromUtf8(
	                  (java.lang.String) ref);
	          className_ = b;
	          return b;
	        } else {
	          return (com.google.protobuf.ByteString) ref;
	        }
	      }
	      /**
	       * <code>optional string class_name = 2;</code>
	       */
	      public Builder setClassName(
	          java.lang.String value) {
	        if (value == null) {
	    throw new NullPointerException();
	  }
	  bitField0_ |= 0x00000002;
	        className_ = value;
	        onChanged();
	        return this;
	      }
	      /**
	       * <code>optional string class_name = 2;</code>
	       */
	      public Builder clearClassName() {
	        bitField0_ = (bitField0_ & ~0x00000002);
	        className_ = getDefaultInstance().getClassName();
	        onChanged();
	        return this;
	      }
	      /**
	       * <code>optional string class_name = 2;</code>
	       */
	      public Builder setClassNameBytes(
	          com.google.protobuf.ByteString value) {
	        if (value == null) {
	    throw new NullPointerException();
	  }
	  bitField0_ |= 0x00000002;
	        className_ = value;
	        onChanged();
	        return this;
	      }

	      // optional bytes data = 3;
	      private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
	      /**
	       * <code>optional bytes data = 3;</code>
	       */
	      public boolean hasData() {
	        return ((bitField0_ & 0x00000004) == 0x00000004);
	      }
	      /**
	       * <code>optional bytes data = 3;</code>
	       */
	      public com.google.protobuf.ByteString getData() {
	        return data_;
	      }
	      /**
	       * <code>optional bytes data = 3;</code>
	       */
	      public Builder setData(com.google.protobuf.ByteString value) {
	        if (value == null) {
	    throw new NullPointerException();
	  }
	  bitField0_ |= 0x00000004;
	        data_ = value;
	        onChanged();
	        return this;
	      }
	      /**
	       * <code>optional bytes data = 3;</code>
	       */
	      public Builder clearData() {
	        bitField0_ = (bitField0_ & ~0x00000004);
	        data_ = getDefaultInstance().getData();
	        onChanged();
	        return this;
	      }

	      // @@protoc_insertion_point(builder_scope:TeraArray)
	    }

	    static {
	      defaultInstance = new TeraArray(true);
	      defaultInstance.initFields();
	    }

	    // @@protoc_insertion_point(class_scope:TeraArray)
	  }

	  public interface ModDataOrBuilder extends
	      com.google.protobuf.GeneratedMessage.
	          ExtendableMessageOrBuilder<ModData> {

	    // optional string id = 1;
	    /**
	     * <code>optional string id = 1;</code>
	     */
	    boolean hasId();
	    /**
	     * <code>optional string id = 1;</code>
	     */
	    java.lang.String getId();
	    /**
	     * <code>optional string id = 1;</code>
	     */
	    com.google.protobuf.ByteString
	        getIdBytes();

	    // optional .TeraArray data = 2;
	    /**
	     * <code>optional .TeraArray data = 2;</code>
	     */
	    boolean hasData();
	    /**
	     * <code>optional .TeraArray data = 2;</code>
	     */
	    ChunksProtobuf.TeraArray getData();
	    /**
	     * <code>optional .TeraArray data = 2;</code>
	     */
	    ChunksProtobuf.TeraArrayOrBuilder getDataOrBuilder();
	  }
	  /**
	   * Protobuf type {@code ModData}
	   */
	  public static final class ModData extends
	      com.google.protobuf.GeneratedMessage.ExtendableMessage<
	        ModData> implements ModDataOrBuilder {
	    // Use ModData.newBuilder() to construct.
	    private ModData(com.google.protobuf.GeneratedMessage.ExtendableBuilder<ChunksProtobuf.ModData, ?> builder) {
	      super(builder);
	      this.unknownFields = builder.getUnknownFields();
	    }
	    private ModData(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

	    private static final ModData defaultInstance;
	    public static ModData getDefaultInstance() {
	      return defaultInstance;
	    }

	    public ModData getDefaultInstanceForType() {
	      return defaultInstance;
	    }

	    private final com.google.protobuf.UnknownFieldSet unknownFields;
	    @java.lang.Override
	    public final com.google.protobuf.UnknownFieldSet
	        getUnknownFields() {
	      return this.unknownFields;
	    }
	    private ModData(
	        com.google.protobuf.CodedInputStream input,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      initFields();
	      int mutable_bitField0_ = 0;
	      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
	          com.google.protobuf.UnknownFieldSet.newBuilder();
	      try {
	        boolean done = false;
	        while (!done) {
	          int tag = input.readTag();
	          switch (tag) {
	            case 0:
	              done = true;
	              break;
	            default: {
	              if (!parseUnknownField(input, unknownFields,
	                                     extensionRegistry, tag)) {
	                done = true;
	              }
	              break;
	            }
	            case 10: {
	              bitField0_ |= 0x00000001;
	              id_ = input.readBytes();
	              break;
	            }
	            case 18: {
	              ChunksProtobuf.TeraArray.Builder subBuilder = null;
	              if (((bitField0_ & 0x00000002) == 0x00000002)) {
	                subBuilder = data_.toBuilder();
	              }
	              data_ = input.readMessage(ChunksProtobuf.TeraArray.PARSER, extensionRegistry);
	              if (subBuilder != null) {
	                subBuilder.mergeFrom(data_);
	                data_ = subBuilder.buildPartial();
	              }
	              bitField0_ |= 0x00000002;
	              break;
	            }
	          }
	        }
	      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
	        throw e.setUnfinishedMessage(this);
	      } catch (java.io.IOException e) {
	        throw new com.google.protobuf.InvalidProtocolBufferException(
	            e.getMessage()).setUnfinishedMessage(this);
	      } finally {
	        this.unknownFields = unknownFields.build();
	        makeExtensionsImmutable();
	      }
	    }
	    public static final com.google.protobuf.Descriptors.Descriptor
	        getDescriptor() {
	      return ChunksProtobuf.internal_static_ModData_descriptor;
	    }

	    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
	        internalGetFieldAccessorTable() {
	      return ChunksProtobuf.internal_static_ModData_fieldAccessorTable
	          .ensureFieldAccessorsInitialized(
	              ChunksProtobuf.ModData.class, ChunksProtobuf.ModData.Builder.class);
	    }

	    public static com.google.protobuf.Parser<ModData> PARSER =
	        new com.google.protobuf.AbstractParser<ModData>() {
	      public ModData parsePartialFrom(
	          com.google.protobuf.CodedInputStream input,
	          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	          throws com.google.protobuf.InvalidProtocolBufferException {
	        return new ModData(input, extensionRegistry);
	      }
	    };

	    @java.lang.Override
	    public com.google.protobuf.Parser<ModData> getParserForType() {
	      return PARSER;
	    }

	    private int bitField0_;
	    // optional string id = 1;
	    public static final int ID_FIELD_NUMBER = 1;
	    private java.lang.Object id_;
	    /**
	     * <code>optional string id = 1;</code>
	     */
	    public boolean hasId() {
	      return ((bitField0_ & 0x00000001) == 0x00000001);
	    }
	    /**
	     * <code>optional string id = 1;</code>
	     */
	    public java.lang.String getId() {
	      java.lang.Object ref = id_;
	      if (ref instanceof java.lang.String) {
	        return (java.lang.String) ref;
	      } else {
	        com.google.protobuf.ByteString bs = 
	            (com.google.protobuf.ByteString) ref;
	        java.lang.String s = bs.toStringUtf8();
	        if (bs.isValidUtf8()) {
	          id_ = s;
	        }
	        return s;
	      }
	    }
	    /**
	     * <code>optional string id = 1;</code>
	     */
	    public com.google.protobuf.ByteString
	        getIdBytes() {
	      java.lang.Object ref = id_;
	      if (ref instanceof java.lang.String) {
	        com.google.protobuf.ByteString b = 
	            com.google.protobuf.ByteString.copyFromUtf8(
	                (java.lang.String) ref);
	        id_ = b;
	        return b;
	      } else {
	        return (com.google.protobuf.ByteString) ref;
	      }
	    }

	    // optional .TeraArray data = 2;
	    public static final int DATA_FIELD_NUMBER = 2;
	    private ChunksProtobuf.TeraArray data_;
	    /**
	     * <code>optional .TeraArray data = 2;</code>
	     */
	    public boolean hasData() {
	      return ((bitField0_ & 0x00000002) == 0x00000002);
	    }
	    /**
	     * <code>optional .TeraArray data = 2;</code>
	     */
	    public ChunksProtobuf.TeraArray getData() {
	      return data_;
	    }
	    /**
	     * <code>optional .TeraArray data = 2;</code>
	     */
	    public ChunksProtobuf.TeraArrayOrBuilder getDataOrBuilder() {
	      return data_;
	    }

	    private void initFields() {
	      id_ = "";
	      data_ = ChunksProtobuf.TeraArray.getDefaultInstance();
	    }
	    private byte memoizedIsInitialized = -1;
	    public final boolean isInitialized() {
	      byte isInitialized = memoizedIsInitialized;
	      if (isInitialized != -1) return isInitialized == 1;

	      if (hasData()) {
	        if (!getData().isInitialized()) {
	          memoizedIsInitialized = 0;
	          return false;
	        }
	      }
	      if (!extensionsAreInitialized()) {
	        memoizedIsInitialized = 0;
	        return false;
	      }
	      memoizedIsInitialized = 1;
	      return true;
	    }

	    public void writeTo(com.google.protobuf.CodedOutputStream output)
	                        throws java.io.IOException {
	      getSerializedSize();
	      com.google.protobuf.GeneratedMessage
	        .ExtendableMessage<ChunksProtobuf.ModData>.ExtensionWriter extensionWriter =
	          newExtensionWriter();
	      if (((bitField0_ & 0x00000001) == 0x00000001)) {
	        output.writeBytes(1, getIdBytes());
	      }
	      if (((bitField0_ & 0x00000002) == 0x00000002)) {
	        output.writeMessage(2, data_);
	      }
	      extensionWriter.writeUntil(536870912, output);
	      getUnknownFields().writeTo(output);
	    }

	    private int memoizedSerializedSize = -1;
	    public int getSerializedSize() {
	      int size = memoizedSerializedSize;
	      if (size != -1) return size;

	      size = 0;
	      if (((bitField0_ & 0x00000001) == 0x00000001)) {
	        size += com.google.protobuf.CodedOutputStream
	          .computeBytesSize(1, getIdBytes());
	      }
	      if (((bitField0_ & 0x00000002) == 0x00000002)) {
	        size += com.google.protobuf.CodedOutputStream
	          .computeMessageSize(2, data_);
	      }
	      size += extensionsSerializedSize();
	      size += getUnknownFields().getSerializedSize();
	      memoizedSerializedSize = size;
	      return size;
	    }

	    private static final long serialVersionUID = 0L;
	    @java.lang.Override
	    protected java.lang.Object writeReplace()
	        throws java.io.ObjectStreamException {
	      return super.writeReplace();
	    }

	    public static ChunksProtobuf.ModData parseFrom(
	        com.google.protobuf.ByteString data)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      return PARSER.parseFrom(data);
	    }
	    public static ChunksProtobuf.ModData parseFrom(
	        com.google.protobuf.ByteString data,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      return PARSER.parseFrom(data, extensionRegistry);
	    }
	    public static ChunksProtobuf.ModData parseFrom(byte[] data)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      return PARSER.parseFrom(data);
	    }
	    public static ChunksProtobuf.ModData parseFrom(
	        byte[] data,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws com.google.protobuf.InvalidProtocolBufferException {
	      return PARSER.parseFrom(data, extensionRegistry);
	    }
	    public static ChunksProtobuf.ModData parseFrom(java.io.InputStream input)
	        throws java.io.IOException {
	      return PARSER.parseFrom(input);
	    }
	    public static ChunksProtobuf.ModData parseFrom(
	        java.io.InputStream input,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws java.io.IOException {
	      return PARSER.parseFrom(input, extensionRegistry);
	    }
	    public static ChunksProtobuf.ModData parseDelimitedFrom(java.io.InputStream input)
	        throws java.io.IOException {
	      return PARSER.parseDelimitedFrom(input);
	    }
	    public static ChunksProtobuf.ModData parseDelimitedFrom(
	        java.io.InputStream input,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws java.io.IOException {
	      return PARSER.parseDelimitedFrom(input, extensionRegistry);
	    }
	    public static ChunksProtobuf.ModData parseFrom(
	        com.google.protobuf.CodedInputStream input)
	        throws java.io.IOException {
	      return PARSER.parseFrom(input);
	    }
	    public static ChunksProtobuf.ModData parseFrom(
	        com.google.protobuf.CodedInputStream input,
	        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	        throws java.io.IOException {
	      return PARSER.parseFrom(input, extensionRegistry);
	    }

	    public static Builder newBuilder() { return Builder.create(); }
	    public Builder newBuilderForType() { return newBuilder(); }
	    public static Builder newBuilder(ChunksProtobuf.ModData prototype) {
	      return newBuilder().mergeFrom(prototype);
	    }
	    public Builder toBuilder() { return newBuilder(this); }

	    @java.lang.Override
	    protected Builder newBuilderForType(
	        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
	      Builder builder = new Builder(parent);
	      return builder;
	    }
	    /**
	     * Protobuf type {@code ModData}
	     */
	    public static final class Builder extends
	        com.google.protobuf.GeneratedMessage.ExtendableBuilder<
	          ChunksProtobuf.ModData, Builder> implements ChunksProtobuf.ModDataOrBuilder {
	      public static final com.google.protobuf.Descriptors.Descriptor
	          getDescriptor() {
	        return ChunksProtobuf.internal_static_ModData_descriptor;
	      }

	      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
	          internalGetFieldAccessorTable() {
	        return ChunksProtobuf.internal_static_ModData_fieldAccessorTable
	            .ensureFieldAccessorsInitialized(
	                ChunksProtobuf.ModData.class, ChunksProtobuf.ModData.Builder.class);
	      }

	      // Construct using ChunksProtobuf.ModData.newBuilder()
	      private Builder() {
	        maybeForceBuilderInitialization();
	      }

	      private Builder(
	          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
	        super(parent);
	        maybeForceBuilderInitialization();
	      }
	      private void maybeForceBuilderInitialization() {
	        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
	          getDataFieldBuilder();
	        }
	      }
	      private static Builder create() {
	        return new Builder();
	      }

	      public Builder clear() {
	        super.clear();
	        id_ = "";
	        bitField0_ = (bitField0_ & ~0x00000001);
	        if (dataBuilder_ == null) {
	          data_ = ChunksProtobuf.TeraArray.getDefaultInstance();
	        } else {
	          dataBuilder_.clear();
	        }
	        bitField0_ = (bitField0_ & ~0x00000002);
	        return this;
	      }

	      public Builder clone() {
	        return create().mergeFrom(buildPartial());
	      }

	      public com.google.protobuf.Descriptors.Descriptor
	          getDescriptorForType() {
	        return ChunksProtobuf.internal_static_ModData_descriptor;
	      }

	      public ChunksProtobuf.ModData getDefaultInstanceForType() {
	        return ChunksProtobuf.ModData.getDefaultInstance();
	      }

	      public ChunksProtobuf.ModData build() {
	        ChunksProtobuf.ModData result = buildPartial();
	        if (!result.isInitialized()) {
	          throw newUninitializedMessageException(result);
	        }
	        return result;
	      }

	      public ChunksProtobuf.ModData buildPartial() {
	        ChunksProtobuf.ModData result = new ChunksProtobuf.ModData(this);
	        int from_bitField0_ = bitField0_;
	        int to_bitField0_ = 0;
	        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
	          to_bitField0_ |= 0x00000001;
	        }
	        result.id_ = id_;
	        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
	          to_bitField0_ |= 0x00000002;
	        }
	        if (dataBuilder_ == null) {
	          result.data_ = data_;
	        } else {
	          result.data_ = dataBuilder_.build();
	        }
	        result.bitField0_ = to_bitField0_;
	        onBuilt();
	        return result;
	      }

	      public Builder mergeFrom(com.google.protobuf.Message other) {
	        if (other instanceof ChunksProtobuf.ModData) {
	          return mergeFrom((ChunksProtobuf.ModData)other);
	        } else {
	          super.mergeFrom(other);
	          return this;
	        }
	      }

	      public Builder mergeFrom(ChunksProtobuf.ModData other) {
	        if (other == ChunksProtobuf.ModData.getDefaultInstance()) return this;
	        if (other.hasId()) {
	          bitField0_ |= 0x00000001;
	          id_ = other.id_;
	          onChanged();
	        }
	        if (other.hasData()) {
	          mergeData(other.getData());
	        }
	        this.mergeExtensionFields(other);
	        this.mergeUnknownFields(other.getUnknownFields());
	        return this;
	      }

	      public final boolean isInitialized() {
	        if (hasData()) {
	          if (!getData().isInitialized()) {
	            
	            return false;
	          }
	        }
	        if (!extensionsAreInitialized()) {
	          
	          return false;
	        }
	        return true;
	      }

	      public Builder mergeFrom(
	          com.google.protobuf.CodedInputStream input,
	          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
	          throws java.io.IOException {
	        ChunksProtobuf.ModData parsedMessage = null;
	        try {
	          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
	        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
	          parsedMessage = (ChunksProtobuf.ModData) e.getUnfinishedMessage();
	          throw e;
	        } finally {
	          if (parsedMessage != null) {
	            mergeFrom(parsedMessage);
	          }
	        }
	        return this;
	      }
	      private int bitField0_;

	      // optional string id = 1;
	      private java.lang.Object id_ = "";
	      /**
	       * <code>optional string id = 1;</code>
	       */
	      public boolean hasId() {
	        return ((bitField0_ & 0x00000001) == 0x00000001);
	      }
	      /**
	       * <code>optional string id = 1;</code>
	       */
	      public java.lang.String getId() {
	        java.lang.Object ref = id_;
	        if (!(ref instanceof java.lang.String)) {
	          java.lang.String s = ((com.google.protobuf.ByteString) ref)
	              .toStringUtf8();
	          id_ = s;
	          return s;
	        } else {
	          return (java.lang.String) ref;
	        }
	      }
	      /**
	       * <code>optional string id = 1;</code>
	       */
	      public com.google.protobuf.ByteString
	          getIdBytes() {
	        java.lang.Object ref = id_;
	        if (ref instanceof String) {
	          com.google.protobuf.ByteString b = 
	              com.google.protobuf.ByteString.copyFromUtf8(
	                  (java.lang.String) ref);
	          id_ = b;
	          return b;
	        } else {
	          return (com.google.protobuf.ByteString) ref;
	        }
	      }
	      /**
	       * <code>optional string id = 1;</code>
	       */
	      public Builder setId(
	          java.lang.String value) {
	        if (value == null) {
	    throw new NullPointerException();
	  }
	  bitField0_ |= 0x00000001;
	        id_ = value;
	        onChanged();
	        return this;
	      }
	      /**
	       * <code>optional string id = 1;</code>
	       */
	      public Builder clearId() {
	        bitField0_ = (bitField0_ & ~0x00000001);
	        id_ = getDefaultInstance().getId();
	        onChanged();
	        return this;
	      }
	      /**
	       * <code>optional string id = 1;</code>
	       */
	      public Builder setIdBytes(
	          com.google.protobuf.ByteString value) {
	        if (value == null) {
	    throw new NullPointerException();
	  }
	  bitField0_ |= 0x00000001;
	        id_ = value;
	        onChanged();
	        return this;
	      }

	      // optional .TeraArray data = 2;
	      private ChunksProtobuf.TeraArray data_ = ChunksProtobuf.TeraArray.getDefaultInstance();
	      private com.google.protobuf.SingleFieldBuilder<
	          ChunksProtobuf.TeraArray, ChunksProtobuf.TeraArray.Builder, ChunksProtobuf.TeraArrayOrBuilder> dataBuilder_;
	      /**
	       * <code>optional .TeraArray data = 2;</code>
	       */
	      public boolean hasData() {
	        return ((bitField0_ & 0x00000002) == 0x00000002);
	      }
	      /**
	       * <code>optional .TeraArray data = 2;</code>
	       */
	      public ChunksProtobuf.TeraArray getData() {
	        if (dataBuilder_ == null) {
	          return data_;
	        } else {
	          return dataBuilder_.getMessage();
	        }
	      }
	      /**
	       * <code>optional .TeraArray data = 2;</code>
	       */
	      public Builder setData(ChunksProtobuf.TeraArray value) {
	        if (dataBuilder_ == null) {
	          if (value == null) {
	            throw new NullPointerException();
	          }
	          data_ = value;
	          onChanged();
	        } else {
	          dataBuilder_.setMessage(value);
	        }
	        bitField0_ |= 0x00000002;
	        return this;
	      }
	      /**
	       * <code>optional .TeraArray data = 2;</code>
	       */
	      public Builder setData(
	          ChunksProtobuf.TeraArray.Builder builderForValue) {
	        if (dataBuilder_ == null) {
	          data_ = builderForValue.build();
	          onChanged();
	        } else {
	          dataBuilder_.setMessage(builderForValue.build());
	        }
	        bitField0_ |= 0x00000002;
	        return this;
	      }
	      /**
	       * <code>optional .TeraArray data = 2;</code>
	       */
	      public Builder mergeData(ChunksProtobuf.TeraArray value) {
	        if (dataBuilder_ == null) {
	          if (((bitField0_ & 0x00000002) == 0x00000002) &&
	              data_ != ChunksProtobuf.TeraArray.getDefaultInstance()) {
	            data_ =
	              ChunksProtobuf.TeraArray.newBuilder(data_).mergeFrom(value).buildPartial();
	          } else {
	            data_ = value;
	          }
	          onChanged();
	        } else {
	          dataBuilder_.mergeFrom(value);
	        }
	        bitField0_ |= 0x00000002;
	        return this;
	      }
	      /**
	       * <code>optional .TeraArray data = 2;</code>
	       */
	      public Builder clearData() {
	        if (dataBuilder_ == null) {
	          data_ = ChunksProtobuf.TeraArray.getDefaultInstance();
	          onChanged();
	        } else {
	          dataBuilder_.clear();
	        }
	        bitField0_ = (bitField0_ & ~0x00000002);
	        return this;
	      }
	      /**
	       * <code>optional .TeraArray data = 2;</code>
	       */
	      public ChunksProtobuf.TeraArray.Builder getDataBuilder() {
	        bitField0_ |= 0x00000002;
	        onChanged();
	        return getDataFieldBuilder().getBuilder();
	      }
	      /**
	       * <code>optional .TeraArray data = 2;</code>
	       */
	      public ChunksProtobuf.TeraArrayOrBuilder getDataOrBuilder() {
	        if (dataBuilder_ != null) {
	          return dataBuilder_.getMessageOrBuilder();
	        } else {
	          return data_;
	        }
	      }
	      /**
	       * <code>optional .TeraArray data = 2;</code>
	       */
	      private com.google.protobuf.SingleFieldBuilder<
	          ChunksProtobuf.TeraArray, ChunksProtobuf.TeraArray.Builder, ChunksProtobuf.TeraArrayOrBuilder> 
	          getDataFieldBuilder() {
	        if (dataBuilder_ == null) {
	          dataBuilder_ = new com.google.protobuf.SingleFieldBuilder<
	              ChunksProtobuf.TeraArray, ChunksProtobuf.TeraArray.Builder, ChunksProtobuf.TeraArrayOrBuilder>(
	                  data_,
	                  getParentForChildren(),
	                  isClean());
	          data_ = null;
	        }
	        return dataBuilder_;
	      }

	      // @@protoc_insertion_point(builder_scope:ModData)
	    }

	    static {
	      defaultInstance = new ModData(true);
	      defaultInstance.initFields();
	    }

	    // @@protoc_insertion_point(class_scope:ModData)
	  }

	  private static com.google.protobuf.Descriptors.Descriptor
	    internal_static_TeraArray_descriptor;
	  private static
	    com.google.protobuf.GeneratedMessage.FieldAccessorTable
	      internal_static_TeraArray_fieldAccessorTable;
	  private static com.google.protobuf.Descriptors.Descriptor
	    internal_static_ModData_descriptor;
	  private static
	    com.google.protobuf.GeneratedMessage.FieldAccessorTable
	      internal_static_ModData_fieldAccessorTable;

	  public static com.google.protobuf.Descriptors.FileDescriptor
	      getDescriptor() {
	    return descriptor;
	  }
	  private static com.google.protobuf.Descriptors.FileDescriptor
	      descriptor;
	  static {
	    java.lang.String[] descriptorData = {
	      "\n\014Chunks.proto\"M\n\tTeraArray\022\023\n\004type\030\001 \001(" +
	      "\0162\005.Type\022\022\n\nclass_name\030\002 \001(\t\022\014\n\004data\030\003 \001" +
	      "(\014*\t\010\210\'\020\200\200\200\200\002\":\n\007ModData\022\n\n\002id\030\001 \001(\t\022\030\n\004" +
	      "data\030\002 \001(\0132\n.TeraArray*\t\010\210\'\020\200\200\200\200\002*\220\001\n\004Ty" +
	      "pe\022\013\n\007Unknown\020\000\022\022\n\016DenseArray4Bit\020\001\022\022\n\016D" +
	      "enseArray8Bit\020\002\022\023\n\017DenseArray16Bit\020\003\022\023\n\017" +
	      "SparseArray4Bit\020\004\022\023\n\017SparseArray8Bit\020\005\022\024" +
	      "\n\020SparseArray16Bit\020\006B+\n\027org.terasology.p" +
	      "rotobufB\016ChunksProtobufH\001"
	    };
	    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
	      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
	        public com.google.protobuf.ExtensionRegistry assignDescriptors(
	            com.google.protobuf.Descriptors.FileDescriptor root) {
	          descriptor = root;
	          internal_static_TeraArray_descriptor =
	            getDescriptor().getMessageTypes().get(0);
	          internal_static_TeraArray_fieldAccessorTable = new
	            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
	              internal_static_TeraArray_descriptor,
	              new java.lang.String[] { "Type", "ClassName", "Data", });
	          internal_static_ModData_descriptor =
	            getDescriptor().getMessageTypes().get(1);
	          internal_static_ModData_fieldAccessorTable = new
	            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
	              internal_static_ModData_descriptor,
	              new java.lang.String[] { "Id", "Data", });
	          return null;
	        }
	      };
	    com.google.protobuf.Descriptors.FileDescriptor
	      .internalBuildGeneratedFileFrom(descriptorData,
	        new com.google.protobuf.Descriptors.FileDescriptor[] {
	        }, assigner);
	  }

	  // @@protoc_insertion_point(outer_class_scope)


}
