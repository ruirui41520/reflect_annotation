/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.example.zdd_aidlserver;
// Declare any non-default types here with import statements

public interface IMyAidlInterface extends android.os.IInterface
{
  /** Default implementation for IMyAidlInterface. */
  public static class Default implements com.example.zdd_aidlserver.IMyAidlInterface
  {
    @Override public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException
    {
    }
    @Override public void login(java.lang.String userName, java.lang.String password) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.example.zdd_aidlserver.IMyAidlInterface
  {
    private static final java.lang.String DESCRIPTOR = "com.example.zdd_aidlserver.IMyAidlInterface";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.example.zdd_aidlserver.IMyAidlInterface interface,
     * generating a proxy if needed.
     */
    public static com.example.zdd_aidlserver.IMyAidlInterface asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.example.zdd_aidlserver.IMyAidlInterface))) {
        return ((com.example.zdd_aidlserver.IMyAidlInterface)iin);
      }
      return new com.example.zdd_aidlserver.IMyAidlInterface.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_basicTypes:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          long _arg1;
          _arg1 = data.readLong();
          boolean _arg2;
          _arg2 = (0!=data.readInt());
          float _arg3;
          _arg3 = data.readFloat();
          double _arg4;
          _arg4 = data.readDouble();
          java.lang.String _arg5;
          _arg5 = data.readString();
          this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_login:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          this.login(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.example.zdd_aidlserver.IMyAidlInterface
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(anInt);
          _data.writeLong(aLong);
          _data.writeInt(((aBoolean)?(1):(0)));
          _data.writeFloat(aFloat);
          _data.writeDouble(aDouble);
          _data.writeString(aString);
          boolean _status = mRemote.transact(Stub.TRANSACTION_basicTypes, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().basicTypes(anInt, aLong, aBoolean, aFloat, aDouble, aString);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void login(java.lang.String userName, java.lang.String password) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(userName);
          _data.writeString(password);
          boolean _status = mRemote.transact(Stub.TRANSACTION_login, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().login(userName, password);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      public static com.example.zdd_aidlserver.IMyAidlInterface sDefaultImpl;
    }
    static final int TRANSACTION_basicTypes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_login = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    public static boolean setDefaultImpl(com.example.zdd_aidlserver.IMyAidlInterface impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Stub.Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.example.zdd_aidlserver.IMyAidlInterface getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException;
  public void login(java.lang.String userName, java.lang.String password) throws android.os.RemoteException;
}
