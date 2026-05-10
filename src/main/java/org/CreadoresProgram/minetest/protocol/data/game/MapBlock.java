package org.CreadoresProgram.minetest.protocol.data.game;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.io.ByteArrayOutputStream;

public class MapBlock {
    public static final int SIZE = 16;
    public static final int NODE_COUNT = SIZE * SIZE * SIZE;

    public int version;
    public byte flags;
    public int lightingComplete;

    public static class NodeMeta {
        public int index;
        public Map<String, String> values = new HashMap<>();
        public String inventoryData;
    }

    public List<NodeMeta> metadataList = new ArrayList<>();
    
    public int[] param0 = new int[NODE_COUNT];
    public byte[] param1 = new byte[NODE_COUNT];
    public byte[] param2 = new byte[NODE_COUNT];

    public static MapBlock decode(byte[] packetData) throws Exception {
        MapBlock block = new MapBlock();

        byte[] data = decompress(packetData);
        ByteBuf dataBuf = Unpooled.wrappedBuffer(data);

        block.version = dataBuf.readUnsignedByte();
        block.flags = dataBuf.readByte();
        block.lightingComplete = dataBuf.readUnsignedShort();

        dataBuf.readByte();
        dataBuf.readByte();

        for (int i = 0; i < NODE_COUNT; i++) {
            block.param0[i] = dataBuf.readUnsignedShort();
        }
        dataBuf.readBytes(block.param1);
        dataBuf.readBytes(block.param2);

        int metaVersion = dataBuf.readUnsignedByte();
        if (metaVersion != 0) {
            int count = dataBuf.readUnsignedShort();
            for (int i = 0; i < count; i++) {
                NodeMeta meta = new NodeMeta();
                meta.index = dataBuf.readUnsignedShort();

                int numVars = dataBuf.readUnsignedShort();
                for (int j = 0; j < numVars; j++) {
                    String key = readLuantiString(dataBuf);
                    String value = readLuantiString(dataBuf);
                    boolean isPrivate = dataBuf.readByte() != 0;
                    meta.values.put(key, value);
                }

                meta.inventoryData = readLuantiString(dataBuf);

                block.metadataList.add(meta);
            }
        }
        return block;
    }

    public byte[] encode() throws Exception {
        ByteBuf internal = Unpooled.buffer();
        try {
            internal.writeByte(version);
            internal.writeByte(flags);
            internal.writeShort(lightingComplete);
            internal.writeByte(2);
            internal.writeByte(2);

            for (int p0 : param0) internal.writeShort(p0);
            internal.writeBytes(param1);
            internal.writeBytes(param2);

            if (metadataList.isEmpty()) {
                internal.writeByte(0);
            } else {
                internal.writeByte(1);
                internal.writeShort(metadataList.size());

                for (NodeMeta meta : metadataList) {
                    internal.writeShort(meta.index);
                    internal.writeShort(meta.values.size());
                    for (Map.Entry<String, String> entry : meta.values.entrySet()) {
                        writeLuantiString(internal, entry.getKey());
                        writeLuantiString(internal, entry.getValue());
                        internal.writeByte(0);
                    }

                    writeLuantiString(internal, meta.inventoryData);
                }
            }

            internal.writeByte(0);
            internal.writeShort(0);

            byte[] rawInternal = new byte[internal.readableBytes()];
            internal.readBytes(rawInternal);
            byte[] compressed = compress(rawInternal);
            return compressed;
        } finally {
            internal.release();
        }
    }

    private static byte[] decompress(byte[] data) throws Exception {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        byte[] buf = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buf);
            bos.write(buf, 0, count);
        }
        return bos.toByteArray();
    }

    private static byte[] compress(byte[] data) throws Exception {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        byte[] buf = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buf);
            bos.write(buf, 0, count);
        }
        return bos.toByteArray();
    }
    private static String readLuantiString(ByteBuf buffer) {
        int len = buffer.readUnsignedShort();
        if (len == 0) return "";
        byte[] bytes = new byte[len];
        buffer.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}