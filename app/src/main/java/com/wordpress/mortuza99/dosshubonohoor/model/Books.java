package com.wordpress.mortuza99.dosshubonohoor.model;

public class Books {
    String name;
    String image;
    String downloadlink;

    public Books(String name, String image, String downloadlink) {
        this.name = name;
        this.image = image;
        this.downloadlink = downloadlink;
    }

    public Books() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDownloadlink() {
        return downloadlink;
    }

    public void setDownloadlink(String downloadlink) {
        this.downloadlink = downloadlink;
    }
}
