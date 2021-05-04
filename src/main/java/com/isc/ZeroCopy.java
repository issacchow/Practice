package com.isc;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 零拷贝实现
 * 从硬盘通过DMA拷贝内核缓冲区
 * 并通过DMA直接从内核缓冲区读取数据，
 * 发送到网络接口中。
 * <p>
 * 而不
 * 用先从硬盘DMA拷贝到内核缓冲区，
 * 然后从内核缓冲区通过CPU将数据拷贝到用户态缓冲区，
 * 最后再从缓冲区发送到网络接口
 */
public class ZeroCopy {


    public void readFileByCPU(String filePath) throws IOException {
        File file = new File(filePath);
        FileReader reader = new FileReader(file);
        char[] buffer = new char[1024];
        int readCount = -1;
        StringBuilder builder = new StringBuilder();
        Charset utf8 = Charset.forName("utf-8");

        while ((readCount = reader.read(buffer)) > 0) {
            builder.append(new String(buffer));
        }

        System.out.println(buffer);
    }


    public void ReadFileByDMA(String filePath) throws IOException {

        FileChannel fileChannel = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ);
        // 这里需要调用allocateDirect方法,实例一个DirectByteBuffer,才能实现DMA
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 4);
        buffer.clear();
        String content = null;
        Charset utf8 = Charset.forName("utf-8");
        byte[] b = new byte[10];
        int read = fileChannel.read(buffer);
        if (read > 0) {
            // 切换buffer为读状态(position已经处于读取第n个字节的位置，所以要从开头开始读的话，需要设置一下position)
            buffer.flip();
            buffer.get(b);
            content = new String(b, utf8);
            System.out.println("content:");
            System.out.println(content);
            buffer.clear();
        }


        fileChannel.close();

    }


    /**
     * 零拷贝将文件发送到网络接口上(未验证过程是否全部都走DMA)
     *
     * @param filePath
     * @param host
     * @param port
     * @throws IOException
     */
    public void SendFileByDMA(String filePath, String host, int port) throws IOException {
        FileChannel fileChannel = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ);
        SocketChannel clientSkt = SocketChannel.open();
        clientSkt.configureBlocking(true);
        clientSkt.connect(new InetSocketAddress(host, port));


        fileChannel.transferTo(0, fileChannel.size(), clientSkt);
        // 关闭文件
        fileChannel.close();
        // 关闭链接
        clientSkt.close();
    }

    /**
     * 零拷贝接收网络文件(未验证过程是否全部都走DMA)
     *
     * @param saveFilePath
     * @param listenPort
     * @throws IOException
     */
    public void ReceiveFileByDMA(String saveFilePath, int listenPort) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();

        server.bind(new InetSocketAddress(listenPort));

        System.out.println("start to accept new connection");
        // 阻塞
        server.configureBlocking(true);
        SocketChannel conn = server.accept();


        System.out.println("accepted new connection");
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 4);
        FileChannel fileChannel = FileChannel.open(Paths.get(saveFilePath),
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE
        );

        conn.configureBlocking(true);
        while (conn.read(buffer) > 0) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }

        System.out.println("receive file complete");
        fileChannel.close();

    }

}
